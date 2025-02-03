package com.slaxation.kotlinMoro.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.Getter
import lombok.Setter
import java.io.Serializable

import com.slaxation.kotlinMoro.constants.UserConstants
import lombok.AllArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Getter
@Setter
@Table(name = "users")
data class User : Serializable, UserDetails {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long

    @field:Size(min = UserConstants.USERNAME_LENGTH_MIN)
    @field:Size(max = UserConstants.USERNAME_LENGTH_MAX)
    private lateinit var username: String

    @field:Size(min = UserConstants.PASSWORD_LENGTH_MIN)
    private lateinit var password: String

    private lateinit var authorities: List<GrantedAuthority>

    @JsonProperty("name")
    @field:Size(max = UserConstants.NAME_LENGTH_MAX)
    private var name: String = ""
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }
}