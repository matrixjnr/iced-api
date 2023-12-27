package com.icedpath.appstart.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String? = null,

    @Column(unique = true)
    var email: String? = null,

    var password: String? = null,

    @CreationTimestamp
    var createdAt: String? = null,

    @UpdateTimestamp
    var updatedAt: String? = null
): UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        val roles: List<String> = listOf("ROLE_USER")
        return roles.stream()
            .map { role: String? -> SimpleGrantedAuthority(role) }
            .collect(Collectors.toList())
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {
        return email!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}