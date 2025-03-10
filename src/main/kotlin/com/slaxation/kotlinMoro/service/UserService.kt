package com.slaxation.kotlinMoro.service

import com.slaxation.kotlinMoro.dto.ChangePasswordDTO
import com.slaxation.kotlinMoro.dto.RegisterUserDTO
import com.slaxation.kotlinMoro.dto.UpdateUserDTO
import com.slaxation.kotlinMoro.dto.UserDTO
import com.slaxation.kotlinMoro.event.UserUpdatedEvent
import com.slaxation.kotlinMoro.exception.ForbiddenException
import com.slaxation.kotlinMoro.exception.NotFoundException
import com.slaxation.kotlinMoro.exception.enumeration.GenericErrorCode
import com.slaxation.kotlinMoro.mapper.UpdateUserMapper
import com.slaxation.kotlinMoro.mapper.UserMapper
import com.slaxation.kotlinMoro.model.User
import com.slaxation.kotlinMoro.repository.RoleRepository
import com.slaxation.kotlinMoro.repository.UserRepository
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(

    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userMapper: UserMapper,
    private val updateUserMapper: UpdateUserMapper,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val roleRepository: RoleRepository
) : UserDetailsService {


    fun getUserById(id: Long): UserDTO {
        return userMapper.entityToDto(userRepository.findById(id).orElseThrow {
            NotFoundException(GenericErrorCode.NOT_FOUND_ERROR.toString(), "User with userId: $id was not found.")
        })
    }

    @Transactional
    fun registerUser(registerUserDTO: RegisterUserDTO): UserDTO {

        require(userRepository.findByUsername(registerUserDTO.username) == null) { "Username already exists" }

        // Assign default role "USER"
        val defaultRole = roleRepository.findByName("USER")
            ?: throw IllegalStateException("Default role 'USER' not found in database!")

        val encodedPassword = passwordEncoder.encode(registerUserDTO.password)
        val newUser = User(
            username = registerUserDTO.username,
            password = encodedPassword,
            name = registerUserDTO.name,
            roles = setOf(defaultRole)
        )

        return userMapper.entityToDto(userRepository.save(newUser))

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

    fun getAllUsers(): List<UserDTO> {
        return userRepository.findAll()
            .stream()
            .map(userMapper::entityToDto)
            .toList()
    }

    @Transactional
    fun updateLoggedInUser(userRequest: @Valid UpdateUserDTO): UserDTO {
        //retrieve logged in user

        val loggedInUser: UserDetails = getLoggedInUser()

        //check whether logged-in user is same user as the one from updateRequest
        if (userRequest.username != loggedInUser.username) {
            val msg =
                String.format("You need to be logged into '" + userRequest.username + "' to be able to update it!")
            throw ForbiddenException(GenericErrorCode.FORBIDDEN_ERROR.toString(), msg)
        }

        //retrieve UserEntity from database
        val u: User? = userRepository.findByUsername(userRequest.username)

        if (u == null) {
            val msg = "User: ${userRequest.username} was not found."
            throw NotFoundException(GenericErrorCode.NOT_FOUND_ERROR.toString(), msg)
        }

        updateUserMapper.dtoToEntity(userRequest, u)

        //persist changes
        val savedUser: User = userRepository.save(u)

        //trigger event
        applicationEventPublisher.publishEvent(UserUpdatedEvent(this, savedUser.getUsername()))

        //return updated entity
        return userMapper.entityToDto(savedUser)
    }

    fun deleteUser(username: String) {
        val msg = String.format("User: $username was not found.")

        val user = userRepository.findByUsername(username)
            ?: throw NotFoundException(GenericErrorCode.NOT_FOUND_ERROR.toString(), msg)

        userRepository.delete(user)
        SecurityContextHolder.clearContext()
    }

    fun getLoggedInUser(): User {
        return SecurityContextHolder.getContext().authentication.principal as User
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        // Map User entity to UserDetails object
        return user
    }

}