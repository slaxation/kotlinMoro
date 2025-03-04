package com.slaxation.kotlinMoro.model

import com.slaxation.kotlinMoro.constants.UserConstants
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import java.io.Serializable

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @field:Size(min = UserConstants.USERNAME_LENGTH_MIN)
    @field:Size(max = UserConstants.USERNAME_LENGTH_MAX)
    private var username: String,

    @field:Size(min = UserConstants.PASSWORD_LENGTH_MIN)
    private var password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    val roles: Set<Role>,

    @field:Size(max = UserConstants.NAME_LENGTH_MAX)
    var name: String? = null

) : Serializable, UserDetails {


    override fun getUsername(): String {
        return username
    }

    // Override getPassword method for UserDetails interface
    override fun getPassword(): String {
        return password
    }

    fun setPassword(pass: String) {
        password = pass
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }
    }
    override fun isAccountNonExpired() = true  // A method with an expression body, no `{}` required.

    override fun isAccountNonLocked() = true  // Similarly, expression body.

    override fun isCredentialsNonExpired() = true  // Expression body.

    override fun isEnabled() = true  // Expression body.

}