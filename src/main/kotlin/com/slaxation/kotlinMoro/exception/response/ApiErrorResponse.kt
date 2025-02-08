package com.slaxation.kotlinMoro.exception.response

import com.fasterxml.jackson.annotation.JsonInclude

class ApiErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var title: String? = null
    var code: String? = null
    var message: String? = null
}