package com.icedpath.appstart.controller.dto

data class CommentDto(
    val content: String,
    val authorId: Long,
    val postId: Long
)
