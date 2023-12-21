package com.icedpath.appstart.service

import com.icedpath.appstart.controller.dto.CommentDto
import com.icedpath.appstart.controller.dto.PostDto
import com.icedpath.appstart.exception.NotFoundException
import com.icedpath.appstart.model.Comment
import com.icedpath.appstart.model.Post
import com.icedpath.appstart.repository.CommentRepository
import com.icedpath.appstart.repository.PostRepository
import com.icedpath.appstart.repository.UserRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class PostService (
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(PostService::class.java)

    @Transactional
    fun save(postDto: PostDto): Post {
        val user = userRepository.findById(postDto.authorId).orElse(null)
        if (user != null) {
            val post = Post(
                title = postDto.title,
                slug = postDto.slug,
                status = postDto.status,
                type = postDto.type,
                category = postDto.category,
                tags = postDto.tags,
                summary = postDto.summary,
                thumbnail = postDto.thumbnail,
                content = postDto.content,
                author = user
            )
            logger.info("saved post: ${post.title}")
            return postRepository.save(post)
        } else {
            logger.error("User with id ${postDto.authorId} not found")
            throw NotFoundException("User with id ${postDto.authorId} not found")
        }
    }

    fun delete(id: Long) {
        logger.info("delete post with id: $id")
        val foundPost = postRepository.findById(id).orElse(null)
        if (foundPost != null) {
            postRepository.delete(foundPost)
        } else {
            logger.error("Post with id $id not found")
            throw NotFoundException("Post with id $id not found")
        }
    }

    @Transactional
    fun update(post: Post): Post {
        logger.info("update: $post")
        val foundPost = postRepository.findById(post.id!!).orElse(null)
        if (foundPost != null) {
            foundPost.title = post.title
            foundPost.slug = post.slug
            foundPost.status = post.status
            foundPost.type = post.type
            foundPost.category = post.category
            foundPost.tags = post.tags
            foundPost.summary = post.summary
            foundPost.thumbnail = post.thumbnail
            foundPost.content = post.content
            return postRepository.save(foundPost)
        } else {
            logger.error("Post with id ${post.id} not found")
            throw NotFoundException("Post with id ${post.id} not found")
        }
    }

    fun findAll() = postRepository.findAll()

    fun findById(id: Long): Post? {
        logger.info("finding post with id: $id")
        val foundPost = postRepository.findById(id).orElse(null)
        if (foundPost != null) {
            return foundPost
        } else {
            logger.error("Post with id $id not found")
            throw NotFoundException("Post with id $id not found")
        }
    }

    fun findBySlug(slug: String): Post? {
        logger.info("finding post with slug: $slug")
        val foundPost = postRepository.findBySlug(slug).orElse(null)
        if (foundPost != null) {
            return foundPost
        } else {
            logger.error("Post with slug $slug not found")
            throw NotFoundException("Post with slug $slug not found")
        }
    }

    fun findByAuthorId(authorId: Long): List<Post> {
        logger.info("finding posts with authorId: $authorId")
        val foundPosts = postRepository.findByAuthorId(authorId)
        if (foundPosts != null) {
            return foundPosts
        } else {
            logger.error("Posts with authorId $authorId not found")
            throw NotFoundException("Posts with authorId $authorId not found")
        }
    }

    fun findByCategoryId(category: String): List<Post> {
        logger.info("finding posts with category: $category")
        val foundPosts = postRepository.findAllByCategory(category)
        if (foundPosts != null) {
            return foundPosts
        } else {
            logger.error("Posts with category $category not found")
            throw NotFoundException("Posts with category $category not found")
        }
    }

    fun findByTag(tag: String): List<Post> {
        logger.info("finding posts with tag: $tag")
        val foundPosts = postRepository.findAllByTagsContainingIgnoreCase(tag)
        if (foundPosts != null) {
            return foundPosts
        } else {
            logger.error("Posts with tag $tag not found")
            throw NotFoundException("Posts with tag $tag not found")
        }
    }

    fun findByTitle(title: String): List<Post> {
        logger.info("finding posts with title: $title")
        val foundPosts = postRepository.findByTitleContainingIgnoreCase(title)
        if (foundPosts != null) {
            return foundPosts
        } else {
            logger.error("Posts with title $title not found")
            throw NotFoundException("Posts with title $title not found")
        }
    }

    fun findAllByOrderByCreatedAtDesc(): List<Post> {
        logger.info("finding all posts")
        val foundPosts = postRepository.findAllByOrderByCreatedAtDesc()
        if (foundPosts != null) {
            return foundPosts
        } else {
            logger.error("Posts not found")
            throw NotFoundException("Posts not found")
        }
    }

    // add comment
    @Transactional
    fun addComment(commentDto: CommentDto) {
        val user = userRepository.findById(commentDto.authorId).orElse(null)
        val post = postRepository.findById(commentDto.postId).orElse(null)
        if (user != null && post != null) {
            commentRepository.save(
                Comment(
                    content = commentDto.content,
                    author = user,
                    postId = post.id
                )
            )
        } else {
            logger.error("User with id ${commentDto.authorId} or Post with id ${commentDto.postId} not found")
            throw NotFoundException("User with id ${commentDto.authorId} or Post with id ${commentDto.postId} not found")
        }
    }

    // delete comment
    fun deleteComment(id: Long) {
        logger.info("delete comment with id: $id")
        val foundComment = commentRepository.findById(id).orElse(null)
        if (foundComment != null) {
            commentRepository.delete(foundComment)
        } else {
            logger.error("Comment with id $id not found")
            throw NotFoundException("Comment with id $id not found")
        }
    }

    // update comment
    @Transactional
    fun updateComment(comment: Comment): Comment {
        logger.info("update: $comment")
        val foundComment = commentRepository.findById(comment.id!!).orElse(null)
        if (foundComment != null) {
            foundComment.content = comment.content
            return commentRepository.save(foundComment)
        } else {
            logger.error("Comment with id ${comment.id} not found")
            throw NotFoundException("Comment with id ${comment.id} not found")
        }
    }

    // find all comments for a post
    fun findAllCommentsByPostId(postId: Long): List<Comment> {
        logger.info("finding comments with postId: $postId")
        val foundComments = commentRepository.findByPostId(postId)
        if (foundComments != null) {
            return foundComments
        } else {
            logger.error("Comments with postId $postId not found")
            throw NotFoundException("Comments with postId $postId not found")
        }
    }
}