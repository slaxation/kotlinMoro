package com.slaxation.kotlinMoro.exception

import org.springframework.http.HttpStatus

open class BaseException(
    val code: String,
    override val message: String,
    val httpStatus: HttpStatus
) : RuntimeException() {
}
