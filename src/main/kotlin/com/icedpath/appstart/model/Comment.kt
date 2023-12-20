package com.icedpath.appstart.model

import jakarta.persistence.*

@Entity
@Table(name = "comments")
class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var postId: Long? = null

    @ManyToOne
    var author: User? = null

    var content: String? = null
    var createdAt: String? = null
    var updatedAt: String? = null
}