package com.slaxation.kotlinMoro.service

import com.slaxation.kotlinMoro.dto.ChangePasswordDTO
import com.slaxation.kotlinMoro.dto.RegisterUserDTO
import com.slaxation.kotlinMoro.dto.UpdateUserDTO
import com.slaxation.kotlinMoro.dto.UserDTO
import com.slaxation.kotlinMoro.mapper.UserMapper
import com.slaxation.kotlinMoro.model.User
import com.slaxation.kotlinMoro.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(

    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val userMapper: UserMapper
) {

    // Register a new user
    @Transactional
    fun registerUser(registerUserDTO: RegisterUserDTO): UserDTO {
        when {
            userRepository.findByUsername(registerUserDTO.username) != null -> {
                throw IllegalArgumentException("Username already exists")
            }
            else -> {
                val encodedPassword = passwordEncoder.encode(registerUserDTO.password)
                val newUser = User(
                    username = registerUserDTO.username,
                    password = encodedPassword,
                    name = registerUserDTO.name
                )

                return userMapper.entityToDto(userRepository.save(newUser))
            }
        }
    }

    // Update user details (username, name)
    @Transactional
    fun updateUserDetails(userId: Long, updateUserDTO: UpdateUserDTO): User {
        val user = userRepository.findById(userId).orElseThrow {
            UsernameNotFoundException("User not found")
        }

        user.username = updateUserDTO.username
        user.name = updateUserDTO.name

        return userRepository.save(user)
    }

    // Change user password
    @Transactional
    fun changePassword(id: Long, changePasswordDTO: ChangePasswordDTO): String {
        val user = userRepository.findById(id).orElseThrow {
            UsernameNotFoundException("User not found")
        }

        when {
            !passwordEncoder.matches(changePasswordDTO.oldPassword, user.password) -> {
                throw IllegalArgumentException("Old password is incorrect")
            }
            else -> {
                user.password = passwordEncoder.encode(changePasswordDTO.newPassword)
                userRepository.save(user)

                return "Password changed successfully"
            }
        }
    }
}