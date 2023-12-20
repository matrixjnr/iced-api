package com.icedpath.appstart.repository

import com.icedpath.appstart.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Long> {
    fun findByAuthorId(authorId: Long): List<Post>? // 1
    fun findAllByOrderByCreatedAtDesc(): List<Post>? // 2
    fun findByTitleContainingIgnoreCase(title: String): List<Post>? // 3
    fun findAllByCategory(category: String): List<Post>? // 4
    fun findAllByTagsContainingIgnoreCase(tag: String): List<Post>? // 5
    fun findBySlug(slug: String): Post? // 6
}