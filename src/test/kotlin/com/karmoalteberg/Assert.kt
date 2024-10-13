package test.kotlin.com.karmoalteberg

import com.karmoalteberg.generateOutput
import com.karmoalteberg.models.output.Action 
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue


class Assert {
	companion object {
		fun assertCount(list: List<Any>, expected: Int) {
			assertEquals(list.size, expected, "Expected $expected, but got ${list.size}")
		}

		fun assertNotEmpty(list: List<Any>) {
			assertTrue(list.isNotEmpty(), "Expected list to be not empty")
		}
	
		fun assertEmpty(list: List<Any>) {
			assertTrue(list.isEmpty(), "Expected list to be empty")
		}

		fun assertNotEmpty(string: String) {
			assertTrue(string.isNotBlank(), "Expected string to be not empty")
		}
	
		fun assertEmpty(string: String) {
			assertTrue(string.isBlank(), "Expected string to be empty")
		}
	}
}