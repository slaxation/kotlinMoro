package com.slaxation.kotlinMoro.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("user")
data class UserDTO(
    @JsonProperty("id")
    val id: Long?,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("username")
    val username: String
)