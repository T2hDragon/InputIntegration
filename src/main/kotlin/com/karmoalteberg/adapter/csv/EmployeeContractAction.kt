package com.karmoalteberg.adapter.csv

import com.karmoalteberg.models.output.EmployeeContract
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import java.io.File

class EmployeeContractAction() {
	/**
	 * Maps it to <Key, Value> pairs, where the Key is the header and value is the data cell
	 */
	fun get(filePath: String): List<Map<String, String>> {
		return csvReader().readAllWithHeader(File(filePath))
	}
}
