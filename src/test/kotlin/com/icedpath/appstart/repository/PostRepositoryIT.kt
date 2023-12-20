package com.icedpath.appstart.repository

import com.icedpath.appstart.model.Comment
import com.icedpath.appstart.model.Post
import com.icedpath.appstart.model.User
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PostRepositoryIT @Autowired constructor(
    val entityManager: EntityManager,
    val userRepository: UserRepository,
    val postRepository: PostRepository
) {

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
        postRepository.deleteAll()
    }

    @Test
    fun `it should save post`() {
        val user = createUser()
        entityManager.persist(user)
        entityManager.flush()

        val post = createPost(user)
        entityManager.persist(post)
        entityManager.flush()

        val foundPost = post.id?.let { postRepository.findById(it) }

        assertNotNull(foundPost)
        assertEquals(post.id, foundPost?.get()?.id)
    }

    @Test
    fun `it should delete post`() {
        val user = createUser()
        entityManager.persist(user)
        entityManager.flush()

        val post = createPost(user)
        entityManager.persist(post)
        entityManager.flush()

        postRepository.delete(post)

        val foundPost = post.id?.let { postRepository.findById(it) }

        assertEquals(false, foundPost?.isPresent)
    }

    @Test
    fun `it should update post`() {
        val user = createUser()
        entityManager.persist(user)
        entityManager.flush()

        val post = createPost(user)
        entityManager.persist(post)
        entityManager.flush()

        val foundPost = post.id?.let { postRepository.findById(it) }

        foundPost?.get()?.title = "New Title"
        entityManager.persist(foundPost?.get())
        entityManager.flush()

        val updatedPost = post.id?.let { postRepository.findById(it) }

        assertEquals("New Title", updatedPost?.get()?.title)
    }

    @Test
    fun `it should find post by author`() {
        val user = createUser()
        entityManager.persist(user)
        entityManager.flush()

        val post = createPost(user)
        entityManager.persist(post)
        entityManager.flush()

        val foundPost = post.author?.id?.let { postRepository.findByAuthorId(it) }

        assertNotNull(foundPost)
        assertEquals(post.author, foundPost?.get(0)?.author)
    }

    @Test
    fun `it should save comment for post and retrieve it`() {
        val user = createUser()
        entityManager.persist(user)
        entityManager.flush()

        val post = createPost(user)
        entityManager.persist(post)
        entityManager.flush()

        val comment = createComment(user)
        comment.postId = post.id
        entityManager.persist(comment)
        entityManager.flush()

        val foundPost = post.id?.let { postRepository.findById(it) }
        foundPost?.get()?.comments?.addLast(comment)

        assertNotNull(foundPost)
        assertEquals(comment.id, foundPost?.get()?.comments?.get(0)?.id)
    }

    fun createUser(): User {
        val user = User(
            name = "John Doe",
            email = "john@test.com",
            password = "password"
        )
        return user
    }

    fun createPost(user: User): Post {
        val post = Post(
            title = "This is a post",
            content = "This is the content of the post",
            author = user
        )
        return post
    }

    fun createComment(user: User): Comment {
        val comment = Comment(
            content = "This is a comment",
            author = user
        )
        return comment
    }
}