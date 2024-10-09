package com.karmoalteberg.builder

import com.karmoalteberg.models.output.PayComponent
import com.karmoalteberg.models.output.Action
import com.karmoalteberg.transformer.DateTransformer
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.LocalDate

class PayComponentBuilder(
	private val action: Action,
	private val dateTransformer: DateTransformer,
) {
	private var amount: Double? = null
	private var currency: String? = null
	private var startDate: String? = null
	private var endDate: String? = null

	fun build(): Pair<PayComponent?, String?> {
		if (amount == null) {
			return Pair(null, "Amount is required")
		}

		if (currency == null) {
			return Pair(null, "Currency is required")
		}

		if (startDate == null) {
			return Pair(null, "Start date is required")
		}

		if (endDate == null) {
			return Pair(null, "End date is required")
		}

		return Pair(PayComponent(
			amount = amount!!,
			currency = currency!!,
			startDate = startDate!!,
			endDate = endDate!!,
		), null)
	}

	fun withAmount(amount: String): String? {
		if (amount.toDoubleOrNull() == null) {
			return "Invalid amount \"$amount\""
		}
		this.amount = amount.toDouble()

		return null
	}

	fun withCurrency(currency: String): String? {
		if (currency.length != 3) {
			return "Invalid currency code \"$currency\""
		}
		this.currency = currency

		return null
	}

	fun withStartDate(startDate: String): String? {
		val (formattedDate, err) = this.dateTransformer.transformToFormat(startDate)
		if (err != null) {
			return "Failed to create startDate -> " + err.message
		}
		this.startDate = formattedDate

		return null
	}

	fun withEndDate(endDate: String): String? {
		val (formattedDate, err) = this.dateTransformer.transformToFormat(endDate)
		if (err != null) {
			return "Failed to create endDate -> " + err.message
		}
		this.endDate = formattedDate

		return null
	}

}