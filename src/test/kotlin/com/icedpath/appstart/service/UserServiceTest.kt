package com.icedpath.appstart.service

import com.icedpath.appstart.model.User
import com.icedpath.appstart.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger

@ExtendWith(value = [MockitoExtension::class])
class UserServiceTest {
    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserService

    @Test
    fun `it should find user by email`() {
        val email = "john@test.com"
        `when`(userRepository.findByEmail(email)).thenReturn(User(email = email))

        val user = userService.findByEmail(email)

        verify(userRepository).findByEmail(email)
        assertThat(user?.email).isEqualTo(email)
    }

    @Test
    fun `it should save user`() {
        val user = createUser()
        `when`(userRepository.save(user)).thenReturn(user)

        val savedUser = userService.save(user)

        verify(userRepository).save(user)
        assertThat(savedUser).isEqualTo(user)
    }

    @Test
    fun `it should delete user`() {
        val user = createUser()
        user.id = 1
        `when`(userRepository.findById(user.id!!))
            .thenReturn(user.let { java.util.Optional.of(it) })
        doNothing().`when`(userRepository).delete(user)
        userService.delete(user.id!!)

        verify(userRepository).delete(user)
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