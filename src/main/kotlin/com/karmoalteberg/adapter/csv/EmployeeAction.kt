package com.karmoalteberg.adapter.csv

import com.karmoalteberg.models.output.Employee
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import java.io.File

class EmployeeAction() {
	fun get(filePath: String): List<Map<String, String>> {
		return csvReader().readAllWithHeader(File(filePath))
	}
}
