package com.icedpath.appstart.repository

import com.icedpath.appstart.model.User
import jakarta.persistence.EntityManager
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
    fun `it should find user by email`() {
        val user = User(
            name = "John Doe",
            email = "john@test.com",
            password = "password"
        )

        entityManager.persist(user)
        entityManager.flush()

        val foundUser = user.email?.let { userRepository.findByEmail(it) }

        assertNotNull(foundUser)
        assertEquals(user.email, foundUser?.email)
    }
}