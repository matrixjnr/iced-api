package com.icedpath.appstart.repository

import com.icedpath.appstart.model.User
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class UserRepositoryIT @Autowired constructor(
    val entityManager: EntityManager,
    val userRepository: UserRepository
) {

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }

    @Test
    fun `it should save user`() {
        val user = createUser()
        entityManager.persist(user)
        entityManager.flush()

        val foundUser = user.email?.let { userRepository.findByEmail(it) }

        assertNotNull(foundUser)
        assertEquals(user.email, foundUser?.get()?.email)
    }

    @Test
    fun `it should delete user`() {
        val user = createUser()
        entityManager.persist(user)
        entityManager.flush()

        userRepository.delete(user)

        val foundUser = userRepository.findByEmail(user.email!!)

        assertThat(foundUser).isEmpty()
    }

    @Test
    fun `it should update user`() {
        val user = createUser()
        entityManager.persist(user)
        entityManager.flush()

        val foundUser = userRepository.findByEmail(user.email!!)

        foundUser.get().name = "Jane Doe"
        entityManager.persist(foundUser.get())
        entityManager.flush()

        val updatedUser = userRepository.findByEmail(user.email!!)

        assertNotNull(updatedUser.get())
        assertEquals(foundUser.get().name, updatedUser.get().name)
    }

    private fun createUser(): User {
        val user = User(
            name = "John Doe",
            email = "john@test.com",
            password = "password"
        )
        return user
    }
}