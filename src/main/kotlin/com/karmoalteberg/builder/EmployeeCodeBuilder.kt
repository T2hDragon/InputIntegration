
package com.karmoalteberg.builder

import com.karmoalteberg.models.output.PayComponent
import com.karmoalteberg.models.output.Action
import com.karmoalteberg.transformer.DateTransformer
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.LocalDate

class EmployeeCodeBuilder(
	val dateTransformer: DateTransformer
) {
	/**
	 * Key: Date, Value: Order number
	 */
	private var orderNumbers = mutableMapOf<String, Int>()

	/**
	 * Builds an employee code based on the date and the date order number in Hexadecimal.
	 */
	fun build(hireDate: String): Pair<String?, String?> {
		val (dateString, err) = dateTransformer.transformToFormat(hireDate)
		if (err !== null) {
			return Pair(null, "Failed to format date -> $err")
		}
		val orderNumber = orderNumbers.getOrDefault(dateString, -1) + 1
		orderNumbers[dateString!!] = orderNumber
		return Pair(dateString + orderNumber.toString(16).uppercase(), null)
	}
}