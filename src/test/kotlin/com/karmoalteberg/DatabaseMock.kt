package test.kotlin.com.karmoalteberg

import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Connection
import com.karmoalteberg.adapter.mariadb.ReadDatabase


class DatabaseMock {
	companion object {
		fun generate(): ReadDatabase {
			val connection = Mockito.mock(Connection::class.java)

			// Default Behaviour
			val emptyPreparedStatement = Mockito.mock(PreparedStatement::class.java)
            val emptyResultSet = Mockito.mock(ResultSet::class.java)
            `when`(connection.prepareStatement(Mockito.anyString())).thenReturn(emptyPreparedStatement)
            `when`(emptyPreparedStatement.executeQuery()).thenReturn(emptyResultSet)
            `when`(emptyResultSet.next()).thenReturn(false)

			// SELECT MAX(id) AS id FROM salary_component -> id: 1
			val maxSalaryComponentIdStmnt = Mockito.mock(PreparedStatement::class.java)
            val maxSalaryComponentResultSet = Mockito.mock(ResultSet::class.java)
            `when`(connection.prepareStatement("SELECT MAX(id) AS id FROM salary_component")).thenReturn(maxSalaryComponentIdStmnt)
            `when`(maxSalaryComponentIdStmnt.executeQuery()).thenReturn(maxSalaryComponentResultSet)
            `when`(maxSalaryComponentResultSet.next()).thenReturn(true, false)
            `when`(maxSalaryComponentResultSet.getInt("id")).thenReturn(1)

			// SELECT MAX(id) AS id FROM person -> id: 2
			val maxPersonIdStmnt = Mockito.mock(PreparedStatement::class.java)
            val maxPersonResultSet = Mockito.mock(ResultSet::class.java)
            `when`(connection.prepareStatement("SELECT MAX(id) AS id FROM person")).thenReturn(maxPersonIdStmnt)
            `when`(maxPersonIdStmnt.executeQuery()).thenReturn(maxPersonResultSet)
            `when`(maxPersonResultSet.next()).thenReturn(true, false)
            `when`(maxPersonResultSet.getInt("id")).thenReturn(2)


			return ReadDatabase(connection)
		}
	}
}