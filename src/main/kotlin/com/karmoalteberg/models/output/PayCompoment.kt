package com.karmoalteberg.models.output

class PayComponent(
	val amount: Double,
	val currency: String,
	val startDate: String,
	val endDate: String,
) {
	companion object {
		const val DATE_FORMAT = "yyyy-MM-dd"
	}
}