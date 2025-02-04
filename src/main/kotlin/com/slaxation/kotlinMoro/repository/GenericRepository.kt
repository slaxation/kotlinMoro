package com.slaxation.kotlinMoro.repository

import org.springframework.data.jpa.repository.JpaRepository

interface GenericRepository<T> : JpaRepository<T, Long> {
}