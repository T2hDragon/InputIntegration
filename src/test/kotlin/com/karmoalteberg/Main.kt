package test.kotlin.com.karmoalteberg

import com.karmoalteberg.generateOutput
import com.karmoalteberg.models.output.Action 
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals


class MainTest {
	@Test
	fun testMain() {
		val roDatabase = DatabaseMock.generate()
		val personRowsCount = 7
		val salaryComponentRowsCount = 6
		val output = generateOutput(roDatabase, "./test_assets/")

		Assert.assertCount(output, 1)
		Assert.assertNotEmpty(output[0].uuid)
		assertEquals(output[0].fname, "test.csv")
		assertNotNull(output[0].errors)
		Assert.assertCount(output[0].errors!!, 7)
		Assert.assertCount(output[0].payload, 6)
		output[0].payload.forEach {
			Assert.assertNotEmpty(it.employeeCode)
		}

		val employeeContract1 = output[0].payload[0]
		assertEquals(employeeContract1.action, Action.HIRE)
		Assert.assertCount(employeeContract1.payComponents, 1)
		assertEquals(employeeContract1.payComponents[0].amount, 3300.0)
		assertEquals(employeeContract1.payComponents[0].currency, "USD")
		assertEquals(employeeContract1.payComponents[0].startDate, "2022-01-01")
		assertEquals(employeeContract1.payComponents[0].endDate, "2022-01-31")
		assertNotNull(employeeContract1.data)
		assertEquals(employeeContract1.data!!.size, personRowsCount + salaryComponentRowsCount)
		assertEquals(employeeContract1.data["person.id"], 3)
		assertEquals(employeeContract1.data["salary_component.person_id"], 3)
		assertEquals(employeeContract1.data["salary_component.id"], 2)

		val employeeContract2 = output[0].payload[1]
		assertEquals(employeeContract2.action, Action.CHANGE)
		Assert.assertCount(employeeContract2.payComponents, 2)
		assertEquals(employeeContract2.payComponents[0].amount, 4400.0)
		assertEquals(employeeContract2.payComponents[0].currency, "EUR")
		assertEquals(employeeContract2.payComponents[0].startDate, "2022-01-01")
		assertEquals(employeeContract2.payComponents[0].endDate, "2022-01-31")
		assertEquals(employeeContract2.payComponents[1].amount, 500.0)
		assertEquals(employeeContract2.payComponents[1].currency, "EUR")
		assertEquals(employeeContract2.payComponents[1].startDate, "2021-01-01")
		assertEquals(employeeContract2.payComponents[1].endDate, "2021-12-31")
		assertNotNull(employeeContract2.data)
		assertEquals(employeeContract2.data!!.size, personRowsCount + salaryComponentRowsCount)
		assertEquals(employeeContract2.data["person.id"], 4)
		assertEquals(employeeContract2.data["salary_component.person_id"], 4)
		assertEquals(employeeContract2.data["salary_component.id"], 3)

		val employeeContract3 = output[0].payload[2]
		assertEquals(employeeContract3.action, Action.CHANGE)
		Assert.assertCount(employeeContract3.payComponents, 1)
		assertEquals(employeeContract3.payComponents[0].amount, 4100.0)
		assertEquals(employeeContract3.payComponents[0].currency, "EUR")
		assertEquals(employeeContract3.payComponents[0].startDate, "2022-01-01")
		assertEquals(employeeContract3.payComponents[0].endDate, "2022-01-31")
		assertNotNull(employeeContract3.data)
		assertEquals(employeeContract3.data!!.size, personRowsCount + salaryComponentRowsCount)
		assertEquals(employeeContract3.data["person.id"], 5)
		assertEquals(employeeContract3.data["salary_component.person_id"], 5)
		assertEquals(employeeContract3.data["salary_component.id"], 4)

		val employeeContract4 = output[0].payload[3]
		assertEquals(employeeContract4.action, Action.TERMINATE)
		Assert.assertCount(employeeContract4.payComponents, 0)
		assertNotNull(employeeContract4.data)
		assertEquals(employeeContract4.data!!.size, personRowsCount)
		assertEquals(employeeContract4.data["person.id"], 6)

		val employeeContract5 = output[0].payload[4]
		assertEquals(employeeContract5.action, Action.HIRE)
		Assert.assertCount(employeeContract5.payComponents, 0)
		assertNotNull(employeeContract5.data)
		assertEquals(employeeContract5.data!!.size, personRowsCount)
		assertEquals(employeeContract5.data["person.id"], 7)

		val employeeContract6 = output[0].payload[5]
		assertEquals(employeeContract6.action, Action.HIRE)
		Assert.assertCount(employeeContract6.payComponents, 0)
		assertNotNull(employeeContract6.data)
		assertEquals(employeeContract6.data!!.size, personRowsCount)
		assertEquals(employeeContract6.data["person.id"], 8)
	}
}
