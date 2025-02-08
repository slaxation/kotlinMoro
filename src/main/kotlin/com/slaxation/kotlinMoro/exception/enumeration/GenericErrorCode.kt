package com.slaxation.kotlinMoro.exception.enumeration

enum class GenericErrorCode(private val value: String) {
    NOT_FOUND_ERROR("NOT_FOUND_ERROR"),
    FORBIDDEN_ERROR("FORBIDDEN_ERROR");

    override fun toString(): String {
        return value
    }
}
