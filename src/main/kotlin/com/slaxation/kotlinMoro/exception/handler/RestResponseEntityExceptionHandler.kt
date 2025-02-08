package com.slaxation.kotlinMoro.exception.handler

import com.slaxation.kotlinMoro.exception.BaseException
import com.slaxation.kotlinMoro.exception.NotFoundException
import com.slaxation.kotlinMoro.exception.response.ApiErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler {
    @ExceptionHandler(value = [NotFoundException::class, UsernameNotFoundException::class])
    private fun handleApplicationException(exception: BaseException): ResponseEntity<ApiErrorResponse> {
        val apiErrorResponse = getApiErrorResponse(exception)
        return ResponseEntity(apiErrorResponse, exception.httpStatus)
    }

    protected fun getApiErrorResponse(exception: BaseException): ApiErrorResponse {
        val r = ApiErrorResponse()
        r.code = exception.code
        r.message = exception.message

        return r
    }
}
