package com.icedpath.appstart.repository

import com.icedpath.appstart.model.Comment
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
class CommentRepositoryIT @Autowired constructor(
    val entityManager: EntityManager,
    val userRepository: UserRepository,
    val commentRepository: CommentRepository
) {

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
        commentRepository.deleteAll()
    }

    @Test
    fun `it should save comment`() {
        val user = createUser()
        entityManager.persist(user)
        entityManager.flush()

        val comment = createComment(user)
        entityManager.persist(comment)
        entityManager.flush()

        val foundComment = comment.id?.let { commentRepository.findById(it) }

        assertNotNull(foundComment)
        assertEquals(comment.id, foundComment?.get()?.id)
    }

    @Test
    fun `it should delete comment`() {
        val user = createUser()
        entityManager.persist(user)
        entityManager.flush()

        val comment = createComment(user)
        entityManager.persist(comment)
        entityManager.flush()

        commentRepository.delete(comment)

        val foundComment = comment.id?.let { commentRepository.findById(it) }

        assertEquals(false, foundComment?.isPresent)
    }

    @Test
    fun `it should update comment`() {
        val user = createUser()
        entityManager.persist(user)
        entityManager.flush()

        val comment = createComment(user)
        entityManager.persist(comment)
        entityManager.flush()

        val foundComment = comment.id?.let { commentRepository.findById(it) }

        foundComment?.get()?.content = "This is an updated comment"
        entityManager.persist(foundComment?.get())
        entityManager.flush()

        val updatedComment = comment.id?.let { commentRepository.findById(it) }

        assertEquals("This is an updated comment", updatedComment?.get()?.content)
    }

    fun createUser(): User {
        val user = User(
            name = "John Doe",
            email = "john@test.com",
            password = "password"
        )
        return user
    }

    fun createComment(user: User): Comment {
        val comment = Comment(
            content = "This is a comment",
            postId = 1,
            author = user
        )
        return comment
    }
}