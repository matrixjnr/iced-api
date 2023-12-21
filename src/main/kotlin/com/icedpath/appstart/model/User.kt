package com.icedpath.appstart.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

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
)