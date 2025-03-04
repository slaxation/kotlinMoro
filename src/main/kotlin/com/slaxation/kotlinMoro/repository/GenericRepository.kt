package com.slaxation.kotlinMoro.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface GenericRepository<T> : JpaRepository<T, Long> {
}