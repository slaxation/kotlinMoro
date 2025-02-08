package com.slaxation.kotlinMoro.controller

import com.slaxation.kotlinMoro.dto.ChangePasswordDTO
import com.slaxation.kotlinMoro.dto.RegisterUserDTO
import com.slaxation.kotlinMoro.dto.UpdateUserDTO
import com.slaxation.kotlinMoro.dto.UserDTO
import com.slaxation.kotlinMoro.service.UserService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserService) {

    @GetMapping("/id/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserById(@PathVariable id: Long): UserDTO {
        return userService.getUserById(id)
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody @Valid registerUserDTO: RegisterUserDTO): UserDTO {
        return userService.registerUser(registerUserDTO)
    }

    @GetMapping("/all", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUsers(): List<UserDTO> {
        return userService.getAllUsers()
    }

    @PostMapping("/update", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateUser(@RequestBody userRequest: UpdateUserDTO): UserDTO {
        return userService.updateLoggedInUser(userRequest)
    }

    @DeleteMapping("/self-delete")
    fun deleteOwnUserAccount(@AuthenticationPrincipal user: User) {
        userService.deleteUser(user.username)
    }

    @PostMapping("/passwordChange/{id}")
    fun changePassword(@PathVariable id: Long, @RequestBody changePasswordDTO: ChangePasswordDTO) {
        userService.changePassword(id, changePasswordDTO)
    }
}