package com.karmoalteberg.builder

import com.karmoalteberg.models.output.Employee
import com.karmoalteberg.models.output.PayComponent
import com.karmoalteberg.models.output.Action
import kotlin.text.toInt
import kotlin.text.toIntOrNull
import kotlin.text.toBooleanStrictOrNull
import kotlin.text.toDoubleOrNull
import kotlin.text.toDouble

class EmployeeBuilder(
	private val action: Action,
	private val employeeCodeBuilder: EmployeeCodeBuilder,
) {
	private var employeeCode: String? = null
	private var data: Map<String, Any>? = null
	private var payComponents: MutableList<PayComponent> = mutableListOf<PayComponent>()

	fun build(): Pair<Employee?, String?> {
		if (employeeCode == null) {
			return Pair(null, "Employee code is required")
		}

		if (data == null) {
			return Pair(null, "Data is required")
		}

		return Pair(Employee(
			employeeCode = employeeCode!!,
			action = action,
			data = data!!,
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

	fun withData(data: Map<String, String?>): String? {
		val result = data.mapNotNull { (key, value) ->
			when {
				key.isBlank() -> {
					null
				}
				value.isNullOrEmpty() -> {
					key to ""
				}
				value.toIntOrNull() != null -> key to value.toInt()
				value.toBooleanStrictOrNull() != null -> key to value.toBooleanStrict()
				value.toDoubleOrNull() != null -> key to value.toDouble()
				else -> {
					key to value
				}
			}
		}.toMap()
		this.data = result
		return null
	}

	fun addPayComponent(payComponent: PayComponent): String? {
		this.payComponents.add(payComponent)
		return null
	}
}
