package com.karmoalteberg.builder

import com.karmoalteberg.models.output.PayComponent
import com.karmoalteberg.models.output.Action
import com.karmoalteberg.transformer.DateTransformer
import com.karmoalteberg.models.output.SalaryComponent
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.LocalDate

class SalaryComponentBuilder(
	private val action: Action,
	private val dateTransformer: DateTransformer,
) {
	private var id: Int? = null
	private var personId: Int? = null
	private var amount: Double = 0.0
	private var currency: String? = null
	private var startDate: String? = null
	private var endDate: String? = null

	fun build(): Pair<SalaryComponent?, String?> {
		if (currency == null) {
			return Pair(null, "Currency is required")
		}

		if (startDate == null) {
			return Pair(null, "Start date is required")
		}

		if (endDate == null) {
			return Pair(null, "End date is required")
		}

		if (id == null) {
			return Pair(null, "Id is required")
		}

		if (personId == null) {
			return Pair(null, "PersonId is required")
		}

		return Pair(SalaryComponent(
			amount = amount,
			currency = currency!!,
			startDate = startDate!!,
			endDate = endDate!!,
			id = id!!,
			personId = personId!!,
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
			return "Failed to create startDate -> " + err
		}
		this.startDate = formattedDate

		return null
	}

	fun withEndDate(endDate: String): String? {
		val (formattedDate, err) = this.dateTransformer.transformToFormat(endDate)
		if (err != null) {
			return "Failed to create endDate -> " + err
		}
		this.endDate = formattedDate

		return null
	}

	fun withId(id: Int): String? {
		this.id = id

		return null
	}

	fun withPersonId(personId: Int): String? {
		this.personId = personId

		return null
	}
}