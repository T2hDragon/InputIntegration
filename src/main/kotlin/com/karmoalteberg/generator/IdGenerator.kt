package com.karmoalteberg.generator


class IdGenerator(private var latestId: Int = 1) {
	fun generate(): Int {
		return latestId++
	}
}