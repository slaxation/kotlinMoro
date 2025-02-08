package com.slaxation.kotlinMoro.exception

import org.springframework.http.HttpStatus

class ForbiddenException(
    code: String,
    message: String
) : BaseException(
    code,
    message,
    HttpStatus.FORBIDDEN)
