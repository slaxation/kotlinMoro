package com.slaxation.kotlinMoro.service

import com.slaxation.kotlinMoro.dto.RegisterUserDTO
import com.slaxation.kotlinMoro.dto.UserDTO
import com.slaxation.kotlinMoro.mapper.UserMapper
import com.slaxation.kotlinMoro.model.Role
import com.slaxation.kotlinMoro.model.User
import com.slaxation.kotlinMoro.repository.RoleRepository
import com.slaxation.kotlinMoro.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceTest : DescribeSpec({

    val userRepository = mockk<UserRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val userMapper = mockk<UserMapper>()
    val roleRepository = mockk<RoleRepository>()
    val applicationEventPublisher = mockk<ApplicationEventPublisher>()
    val userService = UserService(userRepository, passwordEncoder, userMapper, mockk(), applicationEventPublisher, roleRepository)

    describe("registerUser") {

        it("should register a new user successfully") {
            val registerUserDTO = RegisterUserDTO("newuser", "password", "New User")
            val defaultRole = Role(1L, "USER")
            val encodedPassword = "encodedPassword"
            val newUser = User(
                username = registerUserDTO.username,
                password = encodedPassword,
                name = registerUserDTO.name,
                roles = setOf(defaultRole)
            )

            val userDTO = UserDTO(1L, "New User", "newuser")

            every { userRepository.findByUsername("newuser") } returns null
            every { roleRepository.findByName("USER") } returns defaultRole
            every { passwordEncoder.encode("password") } returns encodedPassword
            every { userMapper.entityToDto(userRepository.save(newUser)) } returns userDTO

            val result = userService.registerUser(registerUserDTO)

            result shouldBe userDTO
            result.name shouldBe "New User"
            result.username shouldBe "newuser"
        }

        it("should throw IllegalArgumentException when username is taken") {
            val registerUserDTO = RegisterUserDTO("existinguser", "password", "Existing User")

            every { userRepository.findByUsername("existinguser") } returns User(1L, "password", "Existing User", emptySet())

            val exception = shouldThrow<IllegalArgumentException> {
                userService.registerUser(registerUserDTO)
            }

            exception.message shouldBe "Username already exists"
            verify { userRepository.findByUsername("existinguser") }
        }

        it("should throw IllegalStateException when default role is not found") {
            val registerUserDTO = RegisterUserDTO("newuser", "password", "New User")

            every { userRepository.findByUsername("newuser") } returns null
            every { roleRepository.findByName("USER") } returns null

            val exception = shouldThrow<IllegalStateException> {
                userService.registerUser(registerUserDTO)
            }

            exception.message shouldBe "Default role 'USER' not found in database!"
            verify { userRepository.findByUsername("newuser") }
            verify { roleRepository.findByName("USER") }
        }
    }
})