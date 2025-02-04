package com.slaxation.kotlinMoro.dto

data class ChangePasswordDTO(
    val oldPassword: String,
    val newPassword: String
)