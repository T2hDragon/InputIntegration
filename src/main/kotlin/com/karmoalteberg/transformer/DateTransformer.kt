package com.karmoalteberg.transformer

import java.io.File
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.LocalDate

/**
 * Transform Date into a unified format.
 * Able to require a singular input date format (Would not allow different input formats if singlularInputDateFormat is true).
 */
class DateTransformer(
	private val outputFormatString: String,
	private val singlularInputDateFormat: Boolean = false,
) {
	private var previousFormat: DateTimeFormatter? = null
	private val outputFormat: DateTimeFormatter = DateTimeFormatter.ofPattern(outputFormatString)

	private val formats = listOf(
		DateTimeFormatter.ofPattern("ddMMyy"),      // 160692 -> 16-06-1992
		DateTimeFormatter.ofPattern("dd-MM-yyyy"),  // 16-06-1992
		DateTimeFormatter.ofPattern("MM-dd-yyyy"),  // 06-16-1992
		DateTimeFormatter.ofPattern("yyyy-MM-dd"),  // 1992-06-16
		DateTimeFormatter.ofPattern("dd/MM/yyyy"),  // 16/06/1992
		DateTimeFormatter.ofPattern("MM/dd/yyyy"),  // 06/16/1992
		DateTimeFormatter.ofPattern("yyyy/MM/dd"),  // 1992/06/16
		DateTimeFormatter.ofPattern("dd.MM.yyyy"),  // 16.06.1992
		DateTimeFormatter.ofPattern("MM.dd.yyyy"),  // 06.16.1992
		DateTimeFormatter.ofPattern("dd MMM yyyy"), // 16 Jun 1992
		DateTimeFormatter.ofPattern("MMM dd, yyyy"),// Jun 16, 1992
		DateTimeFormatter.ofPattern("yyyyMMdd"),    // 19920616
		DateTimeFormatter.ofPattern("dd-MMM-yyyy"), // 16-Jun-1992
		DateTimeFormatter.ofPattern("EEE, MMM dd yyyy"), // Tue, Jun 16 1992
		DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"), // 19920616T120000
		// Add more when client informs on new formats and add examples
	)

	fun transformToFormat(dateString: String): Pair<String?, Error?> {
		for (format in formats) {
			try {
				val date = LocalDate.parse(dateString, format)
				if (singlularInputDateFormat && this.previousFormat == null) {
					this.previousFormat = format
				}

				if (singlularInputDateFormat && !format.equals(this.previousFormat)) {
					val fromFormat = this.previousFormat.toString()
					val toFormat = format.toString()
					return Pair(null, Error("Inconsistent date format, given \"$fromFormat\" excpected \"$toFormat\""))
				}

				return Pair(date.format(this.outputFormat), null)
			} catch (e: DateTimeParseException) {
				// Ignore and continue to try other formats
			}
		}

		return Pair(null, Error("Invalid date format \"$dateString\""))
	}

	fun transformToDate(dateString: String): Pair<LocalDate?, Error?> {
		val (stringFormat, err) = transformToFormat(dateString)
		if (err != null) {
			return Pair(null, err)
		}
		val pattern = DateTimeFormatter.ofPattern(outputFormatString)
		return Pair(LocalDate.parse(stringFormat, pattern), null)
	}
}
