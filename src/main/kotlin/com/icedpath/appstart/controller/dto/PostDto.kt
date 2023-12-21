package com.icedpath.appstart.controller.dto;

data class PostDto (
    val title: String,
    val slug: String,
    val status: String,
    val type: String,
    val category: String,
    val tags: List<String>,
    val summary: String,
    val thumbnail: String,
    val content: String,
    val authorId: Long
)
