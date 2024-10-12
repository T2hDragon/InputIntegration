package com.karmoalteberg

import io.github.cdimascio.dotenv.Dotenv;


class Env {
	val dotenv = Dotenv.load()

	fun GetMariaDbDatabase(): String {
		val mariaDbDatabase = dotenv["MARIADB_DATABASE"]
		if (mariaDbDatabase.isNullOrBlank()) {
			throw Exception("MARIADB_DATABASE is not set in .env")
		}
		return mariaDbDatabase
	}

	fun GetMariaDbUser(): String {
		val mariaDbUser = dotenv["MARIADB_USER"]
		if (mariaDbUser.isNullOrBlank()) {
			throw Exception("MARIADB_USER is not set in .env")
		}
		return mariaDbUser
	}

	fun GetMariaDbPassword(): String {
		val mariaDbPassword = dotenv["MARIADB_PASSWORD"]
		if (mariaDbPassword.isNullOrBlank()) {
			throw Exception("MARIADB_PASSWORD is not set in .env")
		}
		return mariaDbPassword
	}

	fun GetMariaDbServerName(): String {
		val mariaDbUrl = dotenv["MARIADB_SERVER_NAME"]
		if (mariaDbUrl.isNullOrBlank()) {
			throw Exception("MARIADB_SERVER_NAME is not set in .env")
		}
		return mariaDbUrl
	}
}
