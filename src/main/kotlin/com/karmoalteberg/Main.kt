package com.karmoalteberg

import java.io.File
import java.time.format.DateTimeFormatter
import com.karmoalteberg.models.output.EmployeeContract
import com.karmoalteberg.models.output.PayComponent
import com.karmoalteberg.models.output.Request
import com.karmoalteberg.adapter.csv.EmployeeContractAction
import com.karmoalteberg.service.EmployeeAction as EmployeeActionService
import com.karmoalteberg.transformer.DateTransformer
import com.karmoalteberg.builder.EmployeeCodeBuilder
import com.karmoalteberg.generator.IdGenerator
import kotlin.io.println
import java.util.UUID;
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun main(args: Array<String>) {
	val csvFetcher = EmployeeContractAction()
	val dateTransformer = DateTransformer(PayComponent.DATE_FORMAT, true)
	val employeeCodeBuilder = EmployeeCodeBuilder()
	val personIdGenerator = IdGenerator()
	val salarydGenerator = IdGenerator()
	val employeeActionService = EmployeeActionService(dateTransformer, employeeCodeBuilder, personIdGenerator, salarydGenerator)
	val csvFiles = File("./input").walk().filter { it.extension == "csv" }.toList()
	val outputRequests = mutableListOf<Request>()
	csvFiles.forEach {
		val employees= mutableListOf<EmployeeContract>()
		val errors = mutableListOf<String>()
		val csvRows = csvFetcher.get(it.absolutePath)
		csvRows.forEach {
			val (employee, errs) = employeeActionService.createEmployee(it)
			if (errs.isNotEmpty()) {
				errors.addAll(errs)
			}

			if (employee !== null) {
				employees.add(employee)
			}
		}

		outputRequests.add(Request(
			uuid = UUID.randomUUID().toString(),
			fname = it.name,
			errors = if (errors.isNotEmpty()) errors else null,
			payload = employees,
		))
	}

	// Output Requests to json files using Jackson as JSON serializer
	val mapper = ObjectMapper().registerKotlinModule()
	outputRequests.forEach {
		val json = mapper.writeValueAsString(it)
		val fileName = it.fname.substring(0, it.fname.indexOf("."))
		val filePath = "./output/${fileName}.json"
		File(filePath).writeText(json)
		println("Wrote output to $fileName")
	}
}
