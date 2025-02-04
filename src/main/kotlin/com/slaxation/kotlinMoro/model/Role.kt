package com.slaxation.kotlinMoro.model

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    val name: String  // Example: ROLE_USER, ROLE_ADMIN

)