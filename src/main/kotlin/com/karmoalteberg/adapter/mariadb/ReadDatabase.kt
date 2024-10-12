package com.karmoalteberg.adapter.mariadb

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.PreparedStatement
import com.karmoalteberg.Env

class ReadDatabase(private val connection: Connection) {

	fun getPrepareStatement(query: String): PreparedStatement {
		return connection.prepareStatement(query)
	}

	companion object {
		fun create(env: Env): Pair<ReadDatabase, () -> Unit> {
			val url = env.GetMariaDbServerName()
			val database = env.GetMariaDbDatabase()
			val user = env.GetMariaDbUser()
			val password = env.GetMariaDbPassword()
			val connection = DriverManager.getConnection("jdbc:mariadb://$url:3306/$database", user, password)
			val close: () -> Unit = { connection.close() }
			return Pair(ReadDatabase(connection), close)
		}
	}
}
