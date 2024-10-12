package com.karmoalteberg.models.output

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(using = Serializer::class)
enum class Action() {
	HIRE, CHANGE, TERMINATE;
	companion object {
		fun fromString(action: String): Action? {
			return when (action.uppercase()) {
				"HIRE", "ADD" -> HIRE
				"CHANGE", "UPDATE" -> CHANGE
				"TERMINATE", "DELETE" -> TERMINATE
				else -> null
			}
		}
	}

	fun isHire(): Boolean {
		return this == HIRE
	}

	fun isChange(): Boolean {
		return this == CHANGE
	}

	fun isTerminate(): Boolean {
		return this == TERMINATE
	}
}

class Serializer : JsonSerializer<Action>() {
    override fun serialize(value: Action, gen: JsonGenerator, serializers: com.fasterxml.jackson.databind.SerializerProvider) {
        gen.writeString(value.name.lowercase())
    }
}