package com.slaxation.kotlinMoro.repository

import com.slaxation.kotlinMoro.model.Role
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : GenericRepository<Role> {
    fun findByName(name: String): Role?
}