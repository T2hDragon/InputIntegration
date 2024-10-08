package com.karmoalteberg.service

import com.karmoalteberg.models.output.Action
import com.karmoalteberg.models.output.Employee
import com.karmoalteberg.models.output.PayComponent
import com.karmoalteberg.builder.EmployeeBuilder
import com.karmoalteberg.builder.PayComponentBuilder
import com.karmoalteberg.builder.EmployeeCodeBuilder
import com.karmoalteberg.transformer.DateTransformer
import java.time.LocalDateTime

class EmployeeAction(
	val dateTransformer: DateTransformer,
	val employeeCodeBuilder: EmployeeCodeBuilder,
) {
	/**
	 * Is responsible for creating an employee from a map of data
	 * Is responsible for creating components and confirming their existance
	 * Validity of the component contents is checked by the builder
	 */
	fun createEmployee(data: Map<String, String>): Pair<Employee?, MutableList<String>> {
		val errors = mutableListOf<String>()
		val actionString = data["ACTION"]?: ""
		val action = Action.fromString(actionString)
		if (action == null) {

			errors.add("Invalid action \"$actionString\"")
			return Pair(null, errors)
		}
		val employeeBuilder = EmployeeBuilder(action, employeeCodeBuilder)

		var employeeCode = data["contract_workerId"]?: ""
		if (action == Action.HIRE && employeeCode.isBlank()) {
			val employeeHireDateString = data["contract_workStartDate"] ?: ""
			if (employeeHireDateString.isBlank()) {
				errors.add("Employee hire time is required for creating employee code")
			} else {
				val (employeeHireDate, timeFormetErr) = dateTransformer.transformToDate(employeeHireDateString)
				if (timeFormetErr !== null) {
					errors.add("Failed to format employee hire date to create employee code -> " + timeFormetErr)
				} else {
					employeeCode = employeeCodeBuilder.build(employeeHireDate!!)
				}
			}
		}
		var err = employeeBuilder.withEmployeeCode(employeeCode)
		if (err !== null) {
			errors.add(err)
		}

		err = employeeBuilder.withData(data)
		if (err !== null) {
			errors.add(err)
		}

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
				employeeBuilder.addPayComponent(payComponent!!)
			}
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
				employeeBuilder.addPayComponent(payComponent!!)
			}
		}

		val (employee, employeeBuildErr) = employeeBuilder.build()
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

		payComponentBuilder.withStartDate(startDate)
		if (err !== null) {
			return Pair(null, err)
		}

		val endDate = data[endDateKey]
		if (endDate == null) {
			return Pair(null, "\"$endDateKey\" is required")
		}
		payComponentBuilder.withEndDate(endDate)
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