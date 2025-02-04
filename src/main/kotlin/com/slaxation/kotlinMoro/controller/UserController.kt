package com.slaxation.kotlinMoro.controller

import com.slaxation.kotlinMoro.dto.RegisterUserDTO
import com.slaxation.kotlinMoro.dto.UserDTO
import com.slaxation.kotlinMoro.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody @Valid registerUserDTO: RegisterUserDTO): UserDTO {
        return userService.registerUser(registerUserDTO)
    }

    //TODO: rest of the methods
}