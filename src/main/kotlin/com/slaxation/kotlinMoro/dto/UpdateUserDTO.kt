package com.slaxation.kotlinMoro.dto

import com.slaxation.kotlinMoro.constants.UserConstants
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UpdateUserDTO(

    @field:NotNull
    @field:Size(min = UserConstants.USERNAME_LENGTH_MIN, max = UserConstants.USERNAME_LENGTH_MAX)
    val username: String,

    @field:Size(max = UserConstants.USERNAME_LENGTH_MAX)
    val name: String? = null
)