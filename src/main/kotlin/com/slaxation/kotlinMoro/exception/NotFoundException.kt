package com.slaxation.kotlinMoro.exception

import org.springframework.http.HttpStatus

class NotFoundException(code: String?, message: String?) : BaseException(code!!, message!!, HttpStatus.NOT_FOUND)