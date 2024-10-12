package com.karmoalteberg.generator


class IdGenerator() {
	private var id: Int = 1

	fun generate(): Int {
		return id++
	}
}