package com.karmoalteberg

import java.io.File
import com.karmoalteberg.models.output.Employee
import com.karmoalteberg.models.output.PayComponent
import com.karmoalteberg.adapter.csv.EmployeeAction
import com.karmoalteberg.service.EmployeeAction as EmployeeActionService
import com.karmoalteberg.transformer.DateTransformer
import kotlin.io.println

fun main(args: Array<String>) {
	/**
	 * Would add here dependencies to be shared between multiple actions: Example loggers, database connections, authetntication etc.
	 */
	processEmployeeData()
}

private fun processEmployeeData() {
	val csvFetcher = EmployeeAction()
	val dateTransformer = DateTransformer(PayComponent.DATE_FORMAT, true)
	val employeeActionService = EmployeeActionService(dateTransformer)
	val csvFiles = File("./input").walk().filter { it.extension == "csv" }.toList()
	csvFiles.forEach {
		val csvRows = csvFetcher.get(it.absolutePath)
		csvRows.forEach {
			val (employee, errs) = employeeActionService.createEmployee(it)
			println("=====================================")
			if (employee == null) {
				println("NO EMPLOYEE")
			} else {
				println("Employee Code: " + employee.employeeCode)
				println("Employee Action: " + employee.action)
				println(employee.data)
				employee.payComponents.forEach {
					println(it.amount)
					println(it.currency)
					println(it.startDate)
					println(it.endDate)
				}
			}
			if (errs.isNotEmpty()) {
				println(errs)
			} else {
				println("NO ERRORS")
			}
			println("=====================================")
		}
	}
}
