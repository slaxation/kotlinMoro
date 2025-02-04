package com.slaxation.kotlinMoro.repository

import com.slaxation.kotlinMoro.model.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : GenericRepository<User> {
    fun findByUsername(username: String): User?
}