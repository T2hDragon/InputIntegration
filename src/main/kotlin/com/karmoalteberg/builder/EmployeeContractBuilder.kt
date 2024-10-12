package com.karmoalteberg.builder

import com.karmoalteberg.models.output.EmployeeContract
import com.karmoalteberg.models.output.PayComponent
import com.karmoalteberg.models.output.Action
import com.karmoalteberg.models.output.Person
import com.karmoalteberg.models.output.SalaryComponent
import kotlin.text.toInt
import kotlin.text.toIntOrNull
import kotlin.text.toBooleanStrictOrNull
import kotlin.text.toDoubleOrNull
import kotlin.text.toDouble
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EmployeeContractBuilder(
	private val action: Action,
	private val employeeCodeBuilder: EmployeeCodeBuilder,
) {
	private var employeeCode: String? = null
	private var data: MutableMap<String, Any?> = mutableMapOf()
	private var payComponents: MutableList<PayComponent> = mutableListOf<PayComponent>()

	fun build(): Pair<EmployeeContract?, String?> {
		if (employeeCode == null) {
			return Pair(null, "Employee code is required")
		}

		if (data.size == 0) {
			return Pair(null, "Data is required")
		}

		return Pair(EmployeeContract(
			employeeCode = employeeCode!!,
			action = action,
			data = data,
			payComponents = payComponents,
		), null)
	}

	fun withEmployeeCode(employeeCode: String): String? {
		if (employeeCode.isBlank()) {
			return "Employee code is required"
		}
		this.employeeCode = employeeCode
		return null
	}

	fun withPerson(person: Person): String? {
		this.data["person.id"] = person.id
		this.data["person.full_name"] = person.fullName
		this.data["person.gender"] = person.gender
		this.data["person.birthdate"] = person.birthdate
		this.data["person.employee_code"] = person.employeeCode
		this.data["person.hire_date"] = person.hireDate
		this.data["person.termination_date"] = person.terminationDate
		return null
	}

	fun withSalaryComponent(salaryComponent: SalaryComponent): String? {
		this.data["salary_component.id"] = salaryComponent.id
		this.data["salary_component.person_id"] = salaryComponent.personId
		this.data["salary_component.amount"] = salaryComponent.amount
		this.data["salary_component.currency"] = salaryComponent.currency
		this.data["salary_component.start_date"] = salaryComponent.startDate
		this.data["salary_component.end_date"] = salaryComponent.endDate
		return null
	}

	// fun withData(data: Map<String, String?>): String? {
	// 	val result = data.mapNotNull { (key, value) ->
	// 		when {
	// 			key.isBlank() -> {
	// 				null
	// 			}
	// 			value.isNullOrEmpty() -> {
	// 				key to ""
	// 			}
	// 			value.toIntOrNull() != null -> key to value.toInt()
	// 			value.toBooleanStrictOrNull() != null -> key to value.toBooleanStrict()
	// 			value.toDoubleOrNull() != null -> key to value.toDouble()
	// 			else -> {
	// 				key to value
	// 			}
	// 		}
	// 	}.toMap()
	// 	this.data = result
	// 	return null
	// }

	fun addPayComponent(payComponent: PayComponent): String? {
		this.payComponents.add(payComponent)
		return null
	}
}
