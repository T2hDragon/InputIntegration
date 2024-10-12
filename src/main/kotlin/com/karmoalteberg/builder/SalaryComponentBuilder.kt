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
	private var salaryPayComponent: PayComponent? = null
	fun build(): Pair<SalaryComponent?, String?> {


		if (id == null) {
			return Pair(null, "Id is required")
		}

		if (personId == null) {
			return Pair(null, "PersonId is required")
		}

		if (salaryPayComponent == null) {
			return Pair(null, "PayComponent for salary is required")
		}

		return Pair(SalaryComponent(
			amount = salaryPayComponent!!.amount,
			currency = salaryPayComponent!!.currency,
			startDate = salaryPayComponent!!.startDate,
			endDate = salaryPayComponent!!.endDate,
			id = id!!,
			personId = personId!!,
		), null)
	}

	fun withSalaryPayComponent(salaryPayComponent: PayComponent): String? {
		this.salaryPayComponent = salaryPayComponent

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