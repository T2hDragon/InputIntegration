
package com.karmoalteberg.builder

import com.karmoalteberg.models.output.PayComponent
import com.karmoalteberg.models.output.Action
import com.karmoalteberg.transformer.DateTransformer
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.LocalDate

class EmployeeCodeBuilder(
) {
	/**
	 * Key: Date, Value: Order number
	 */
	private var orderNumbers = mutableMapOf<String, Int>()

	/**
	 * Builds an employee code based on the date and the date order number in Hexadecimal.
	 */
	fun build(date: LocalDate): String {
		val dateString = date.format(DateTimeFormatter.ofPattern("yyMMdd"))
		val orderNumber = orderNumbers.getOrDefault(dateString, -1) + 1
		orderNumbers[dateString] = orderNumber
		return dateString + orderNumber.toString(16).uppercase()
	}
}