package com.karmoalteberg.service

import com.karmoalteberg.models.output.Action
import com.karmoalteberg.models.output.EmployeeContract
import com.karmoalteberg.models.output.PayComponent
import com.karmoalteberg.builder.EmployeeContractBuilder
import com.karmoalteberg.builder.PayComponentBuilder
import com.karmoalteberg.builder.EmployeeCodeBuilder
import com.karmoalteberg.builder.PersonBuilder
import com.karmoalteberg.builder.SalaryComponentBuilder
import com.karmoalteberg.transformer.DateTransformer
import com.karmoalteberg.generator.IdGenerator
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EmployeeAction(
	val dateTransformer: DateTransformer,
	val employeeCodeBuilder: EmployeeCodeBuilder,
	val personIdGenerator: IdGenerator,
	val salaryIdGenerator: IdGenerator,
) {
	/**
	 * Is responsible for creating an employee from a map of data
	 * Is responsible for creating components and confirming their existance
	 * Validity of the component contents is checked by the builder
	 */
	fun createEmployee(data: Map<String, String>): Pair<EmployeeContract?, MutableList<String>> {
		val errors = mutableListOf<String>()
		var err: String? = null
		val actionString = data["ACTION"]?: ""
		val action = Action.fromString(actionString)
		if (action == null) {

			errors.add("Invalid action \"$actionString\"")
			return Pair(null, errors)
		}
		val employeeContractBuilder = EmployeeContractBuilder(action, employeeCodeBuilder)
		val personBuilder = PersonBuilder(action, dateTransformer)
		val salaryComponentBuilder = SalaryComponentBuilder(action, dateTransformer)

		var employeeCode = data["contract_workerId"]
		if (action == Action.HIRE && (employeeCode == null || employeeCode.isBlank())) {
			val employeeHireDateString = data["contract_workStartDate"] ?: ""
			if (employeeHireDateString.isBlank()) {
				errors.add("Employee hire time is required for creating employee code")
			} else {
				val employeeCodeBuild = employeeCodeBuilder.build(employeeHireDateString)
				employeeCode = employeeCodeBuild.first
				err = employeeCodeBuild.second
				if (err != null) {
					errors.add("Failed to format employee hire date to create employee code -> $err")
				}
			}
		}

		if (employeeCode.isNullOrBlank()) {
			errors.add("Employee code is required")
			return Pair(null, errors)
		}
		err = employeeContractBuilder.withEmployeeCode(employeeCode)
		if (err !== null) {
			errors.add(err)
		}

		var salaryPayComponent: PayComponent? = null
		if ((data["pay_amount"] ?: "").isNotBlank()  || (data["pay_currency"] ?: "").isNotBlank()) {
			val (payComponent, payComponentErr) = createPayComponent(
				data,
				action,
				"pay_amount",
				"pay_currency",
				"pay_effectiveFrom",
				"pay_effectiveTo",
			)

			if (payComponentErr !== null) {
				errors.add("Unable to make salary component -> " + payComponentErr)
			} else {
				salaryPayComponent = payComponent
				employeeContractBuilder.addPayComponent(payComponent!!)
			}
		} else {
			errors.add("SalaryPayComponent was not made due to missing \"pay_amount\" or \"pay_currency\" field")
		}

		if ((data["compensation_amount"] ?: "").isNotBlank() || (data["compensation_currency"] ?: "").isNotBlank()) {
			val (payComponent, payComponentErr) = createPayComponent(
				data,
				action,
				"compensation_amount",
				"compensation_currency",
				"compensation_effectiveFrom",
				"compensation_effectiveTo",
			)

			if (payComponentErr !== null) {
				errors.add("Unable to make compensation component -> " + payComponentErr)
			} else {
				employeeContractBuilder.addPayComponent(payComponent!!)
			}
		}


		// Create person
		val personId = personIdGenerator.generate()
		err = personBuilder.withId(personId)
		if (err !== null) {
			errors.add("Unable add person id to Person -> " + err)
		}

		val fullName = data["worker_name"]?: ""
		if (fullName.isNotBlank()) {
			err = personBuilder.withFullName(fullName)
			if (err !== null) {
				errors.add(err)
			}
		}

		val gender = data["worker_gender"]?: ""
		if (gender.isNotBlank()) {
			err = personBuilder.withGender(gender)
			if (err !== null) {
				errors.add(err)
			}
		}

		val personalCode = data["worker_personalCode"]?: ""
		if (personalCode.length >= 6) {
			personBuilder.withBirthdate(personalCode.substring(0, 6))
		}

		err = personBuilder.withEmployeeCode(employeeCode)
		if (err !== null) {
			errors.add(err)
		}

		val hireDate = data["contract_workStartDate"] ?: ""
		if (hireDate.isNotBlank()) {
			err = personBuilder.withHireDate(hireDate)
			if (err !== null) {
				errors.add(err)
			}
		}

		val signatureDateString = data["contract_signatureDate"] ?: ""
		if (signatureDateString.isNotBlank()) {
			err = personBuilder.withTerminationDate(signatureDateString)
			if (err !== null) {
				errors.add(err)
			}
		}

		val (person, personBuildErr) = personBuilder.build()
		if (personBuildErr !== null) {
			errors.add("Failed to create Data person -> " + personBuildErr)
		}
		if (person !== null) {
			employeeContractBuilder.withPerson(person)
		}

		// Create salary component
		val salaryId = salaryIdGenerator.generate()
		err = salaryComponentBuilder.withId(salaryId)
		if (err !== null) {
			errors.add("Unable add salary id to SalaryComponent -> " + err)
		}
		
		if (person === null) {
			errors.add("Person id is required for salary component")
		} else {
			err = salaryComponentBuilder.withPersonId(person.id)
			if (err !== null) {
				errors.add("Unable add person id to SalaryComponent -> " + err)
			}
		}

		if (salaryPayComponent !== null) {
			err = salaryComponentBuilder.withSalaryPayComponent(salaryPayComponent)
			if (err !== null) {
				errors.add("Unable add salary pay component to SalaryComponent -> " + err)
			}
		}

		val (salaryComponent, salaryComponentBuildErr) = salaryComponentBuilder.build()
		if (salaryComponentBuildErr !== null) {
			errors.add("Failed to create Data salary component -> " + salaryComponentBuildErr)
		}
		if (salaryComponent !== null) {
			employeeContractBuilder.withSalaryComponent(salaryComponent)

		}

		val (employee, employeeBuildErr) = employeeContractBuilder.build()
		if (employeeBuildErr !== null) {
			errors.add("Failed to create employee -> " + employeeBuildErr)
		}
		return Pair(employee, errors)
	}

	fun createPayComponent(
		data: Map<String, String>,
		action: Action,
		amountKey: String,
		currencyKey: String,
		startDateKey: String,
		endDateKey: String,
	): Pair<PayComponent?, String?> {
		val amount = data[amountKey]
		val payComponentBuilder = PayComponentBuilder(action, dateTransformer)
		if ((amount ?: "").isEmpty()) {
			return Pair(null, "\"$amountKey\" is not given, but \"$currencyKey\" is given")
		}
		var err = payComponentBuilder.withAmount(amount!!)
		if (err !== null) {
			return Pair(null, err)
		}

		val currency = data[currencyKey]
		if ((currency ?: "").isEmpty()) {
			return Pair(null, "\"$currencyKey\" is not given, but \"$amountKey\" is given")
		}
		err = payComponentBuilder.withCurrency(currency!!)
		if (err !== null) {
			return Pair(null, err)
		}

		val startDate = data[startDateKey]
		if (startDate == null) {
			return Pair(null, "\"$startDateKey\" is required")
		}

		err = payComponentBuilder.withStartDate(startDate)
		if (err !== null) {
			return Pair(null, err)
		}

		val endDate = data[endDateKey]
		if (endDate == null) {
			return Pair(null, "\"$endDateKey\" is required")
		}
		err = payComponentBuilder.withEndDate(endDate)
		if (err !== null) {
			return Pair(null, err)
		}
		
		val (payComponent, payComponentBuildErr) = payComponentBuilder.build()
		if (payComponentBuildErr !== null) {
			return Pair(null, "Failed to create payComponent -> " + payComponentBuildErr)
		}
		return Pair(payComponent, null)
	}
}