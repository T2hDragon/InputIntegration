package com.karmoalteberg.models.output

enum class Action {
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