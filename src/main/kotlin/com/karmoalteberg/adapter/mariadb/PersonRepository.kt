package com.karmoalteberg.adapter.mariadb

import com.karmoalteberg.Env

class PersonRepository(private val db: ReadDatabase) {
	fun getIdByEmployeeCode(employeeCode: String): Int? {
		val prepStmnt = db.getPrepareStatement("SELECT id AS id FROM person WHERE employee_code = ?")
		prepStmnt.setString(1, employeeCode)
		val rs = prepStmnt.executeQuery()
		while (rs.next()) {
			return rs.getInt("id")
		}
		return null
	}

	fun getNextId(): Int {
		val prepStmnt = db.getPrepareStatement("SELECT MAX(id) AS id FROM person")
		val rs = prepStmnt.executeQuery()
		while (rs.next()) {
			return rs.getInt("id") + 1
		}
		return 1
	}
}
