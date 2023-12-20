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
    var slug: String? = null
    var status: String? = null
    var type: String? = null
    var category: String? = null

    @ElementCollection
    var tags: List<String> = mutableListOf()

    var summary: String? = null
    var thumbnail: String? = null
    var content: String? = null

    @ManyToOne
    var author: User? = null

    @OneToMany
    var comments: List<Comment> = mutableListOf()

    @CreationTimestamp
    var createdAt: String? = null

    @UpdateTimestamp
    var updatedAt: String? = null
}
