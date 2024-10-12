package com.karmoalteberg.models.output

import java.time.LocalDate

// Data can be string, number, boolean or decimal value. Validate this
class EmployeeContract(
	val employeeCode: String,
	val action: Action,
	val data: Map<String, Any?>?,
	val payComponents: List<PayComponent>,
) {
}
