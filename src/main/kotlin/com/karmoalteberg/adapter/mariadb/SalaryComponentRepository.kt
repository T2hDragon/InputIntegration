package com.karmoalteberg.adapter.mariadb

import com.karmoalteberg.Env

class SalaryComponentRepository(private val db: ReadDatabase) {
	fun getNextId(): Int {
		val prepStmnt = db.getPrepareStatement("SELECT MAX(id) AS id FROM salary_component")
		val rs = prepStmnt.executeQuery()
		while (rs.next()) {
			return rs.getInt("id") + 1
		}
		return 1
	}
}
