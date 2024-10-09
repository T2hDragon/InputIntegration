package com.karmoalteberg.models.output

// Data can be string, number, boolean or decimal value. Validate this
class Employee(
	val employeeCode: String,
	val action: Action,
	val data: Map<String, Any>,
	val payComponents: List<PayComponent>,
) {
}