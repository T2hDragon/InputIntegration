package com.karmoalteberg.models.output

class Request(
	val uuid: String,
	val fname: String,
	val errors: List<String>? = null,
	val payload: List<Employee> = emptyList(),
) {
}