package com.icedpath.appstart.repository

import com.icedpath.appstart.model.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByPostId(postId: Long): List<Comment>?
}