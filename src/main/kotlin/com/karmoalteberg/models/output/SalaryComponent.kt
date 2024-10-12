package com.karmoalteberg.models.output

class SalaryComponent(
	val id: Int,
	val personId: Int,
	val amount: Double,
	val currency: String,
	val startDate: String,
	val endDate: String,
) {
}