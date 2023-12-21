package com.icedpath.appstart.service

import com.icedpath.appstart.exception.NotFoundException
import com.icedpath.appstart.model.User
import com.icedpath.appstart.repository.UserRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Slf4j
class UserService (
    private val userRepository: UserRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)
    fun findByEmail(email: String): User? {
        logger.info("findByEmail: $email")
        return userRepository.findByEmail(email)
    }

    fun save(user: User): User {
        logger.info("save: $user")
        return userRepository.save(user)
    }

    fun delete(id: Long) {
        logger.info("delete user with id: $id")
        val foundUser = userRepository.findById(id).orElse(null)
        if (foundUser != null) {
            userRepository.delete(foundUser)
        } else {
            logger.error("User with id $id not found")
            throw NotFoundException("User with id $id not found")
        }
    }

    fun update(user: User): User {
        logger.info("update: $user")
        val foundUser = userRepository.findById(user.id!!).orElse(null)
        if (foundUser != null) {
            foundUser.name = user.name
            foundUser.email = user.email
            foundUser.password = user.password
            return userRepository.save(foundUser)
        } else {
            logger.error("User with id ${user.id} not found")
            throw NotFoundException("User with id ${user.id} not found")
        }
    }

    fun findAll() = userRepository.findAll()

    fun findById(id: Long): User? {
        logger.info("finding user with id: $id")
        val foundUser = userRepository.findById(id).orElse(null)
        if (foundUser != null) {
            return foundUser
        } else {
            logger.error("User with id $id not found")
            throw NotFoundException("User with id $id not found")
        }
    }
}