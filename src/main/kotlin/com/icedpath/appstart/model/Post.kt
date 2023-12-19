package com.icedpath.appstart.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
@Table(name = "posts")
class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var title: String? = null
    var description: String? = null
    var tags: String? = null

    @ManyToOne
    var user: User? = null

    @CreationTimestamp
    var createdAt: String? = null

    @UpdateTimestamp
    var updatedAt: String? = null
}
