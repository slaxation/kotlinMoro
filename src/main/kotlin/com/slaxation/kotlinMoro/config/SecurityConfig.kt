package com.slaxation.kotlinMoro.config

import com.slaxation.kotlinMoro.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(http: HttpSecurity, userService: UserService): AuthenticationManager {
        val authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder())
        return authManagerBuilder.build()
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/users/create").permitAll()
                    .requestMatchers("/users/self-delete").authenticated()
                    .anyRequest().authenticated()
            }
            .logout { auth ->
                auth
                    .logoutUrl("/logout") // URL for logout
                    .logoutSuccessUrl("/login?logout=true") // Redirect after successful logout
                    .permitAll()
            }
            .httpBasic { }
            .csrf { it.disable() }

        return http.build()
    }
}