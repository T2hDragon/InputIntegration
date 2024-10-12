package com.karmoalteberg.builder

import com.karmoalteberg.models.output.PayComponent
import com.karmoalteberg.models.output.Action
import com.karmoalteberg.transformer.DateTransformer
import com.karmoalteberg.models.output.Person
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.LocalDate

class PersonBuilder(
	private val action: Action,
	private val dateTransformer: DateTransformer,
) {
	private val birthDateTransformer = DateTransformer(PayComponent.DATE_FORMAT)
	private var id: Int? = null
	private var fullName: String? = null
	private var gender: String? = null
	private var birthdate: String? = null
	private var employeeCode: String? = null
	private var hireDate: String? = null
	private var terminationDate: String? = null

	fun build(): Pair<Person?, String?> {
		if (id == null) {
			return Pair(null, "Id is required")
		}

		if (fullName == null) {
			return Pair(null, "Full name is required")
		}

		if (employeeCode == null) {
			return Pair(null, "Employee code is required")
		}

		if (hireDate == null) {
			return Pair(null, "Hire date is required")
		}

		if (action == Action.TERMINATE && terminationDate == null) {
			terminationDate = LocalDate.now().format(DateTimeFormatter.ofPattern(PayComponent.DATE_FORMAT))
		}

		return Pair(Person(
			id = id!!,
			fullName = fullName!!,
			gender = gender,
			birthdate = birthdate,
			employeeCode = employeeCode!!,
			hireDate = hireDate!!,
			terminationDate = terminationDate,
		), null)
	}

	fun withId(id: Int): String? {
		this.id = id

		return null
	}

	fun withFullName(fullName: String): String? {
		this.fullName = fullName

		return null
	}

	fun withGender(gender: String): String? {
		when (gender.uppercase()) {
			"MALE" -> this.gender = "M"
			"FEMALE" -> this.gender = "F"
			else -> return "Person Gender expects to be \"MALE\" or \"FEMALE\", but given \"$gender\""
		}
		return null
	}

	fun withBirthdate(birthdate: String): String? {
		val (birthdateFormatted, err) = birthDateTransformer.transformToFormat(birthdate)
		if (err !== null) {
			return "Invalid birthdate \"$birthdate\""
		}

		this.birthdate = birthdateFormatted
		return null
	}

	fun withEmployeeCode(employeeCode: String): String? {
		this.employeeCode = employeeCode
		return null
	}

	fun withHireDate(hireDate: String): String? {
		val (hireDateFormatted, err) = dateTransformer.transformToFormat(hireDate)
		if (err !== null) {
			return "Invalid hire date \"$hireDate\""
		}

		this.hireDate = hireDateFormatted
		return null
	}

	fun withTerminationDate(terminationDate: String?): String? {
		if (terminationDate == null) {
			if (action == Action.TERMINATE) {
				this.terminationDate = LocalDate.now().format(DateTimeFormatter.ofPattern(PayComponent.DATE_FORMAT))
			}
			return null
		}

		val (terminationDateFormatted, err) = dateTransformer.transformToFormat(terminationDate)
		if (err !== null) {
			return "Invalid termination date \"$terminationDate\""
		}

		this.terminationDate = terminationDateFormatted
		return null
	}
}