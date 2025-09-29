package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.CustomException.UserAlreadyExistsException
import com.kapston.CTU_DB_API.CustomException.UserNotFoundException
import com.kapston.CTU_DB_API.domain.Enums.StatusEnum
import com.kapston.CTU_DB_API.domain.dto.request.LoginRequest
import com.kapston.CTU_DB_API.domain.dto.request.RegisterRequest
import com.kapston.CTU_DB_API.domain.dto.request.TokenRequest
import com.kapston.CTU_DB_API.domain.dto.response.LoginResponse
import com.kapston.CTU_DB_API.domain.entity.UserEntity
import com.kapston.CTU_DB_API.repository.UserRepository
import com.kapston.CTU_DB_API.service.abstraction.UserService
import com.kapston.CTU_DB_API.utility.HashUtils.verifyPassword
import com.kapston.CTU_DB_API.utility.JwtUtils
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserServiceImpl(
    private val userRepo: UserRepository,
    private val jwtUtils: JwtUtils,
    ): UserService {
    override fun create(user: RegisterRequest): String {

        val userExists = userRepo.existsByEmail(user.email)

        if(userExists) throw UserAlreadyExistsException("Email already exists.")

        userRepo.save(user.toEntity())

        return "User created successfully."
    }
    override fun authenticate(user: LoginRequest): LoginResponse {

        val authUser = userRepo.findByEmail(user.email)
            ?: throw BadCredentialsException("Invalid credentials.")

        val isPasswordMatch = user.password
            .verifyPassword(authUser.password)

        if(!isPasswordMatch)
            throw BadCredentialsException("Invalid credentials.")

        val accessToken = jwtUtils.generateAccessToken(authUser.id.toString())
        val refreshToken = jwtUtils.generateRefreshToken(authUser.id.toString())

        val authToken = TokenRequest(
            id = authUser.id!!,
            hashedAccessToken = accessToken,
            hashedRefreshToken = refreshToken
        )

        return LoginResponse(
            userResponse = authUser.toResponse(),
            authorization = authToken
        )
    }

    override fun getUserEntity(id: UUID): UserEntity {
        return userRepo.findById(id)
            .orElseThrow { UserNotFoundException("No user found.") }
    }

    override fun updateStatus(id: UUID): String {
        val user = userRepo.findById(id)
            .orElseThrow { UserNotFoundException("It seems that this user does not exists.") }

        val updatedStatus = when(user.status) {
            StatusEnum.ACTIVE -> StatusEnum.INACTIVE
            StatusEnum.INACTIVE -> StatusEnum.ACTIVE
        }

        val updatedUser = user.apply {
            status = updatedStatus
        }

        userRepo.save(updatedUser)

        return "User set to $updatedStatus"
    }

    override fun resetPassword(id: UUID, newPassword: String): String {

        val user = getUserEntity(id)

        val updatedUser = user.apply {
            password = newPassword
        }
        userRepo.save(updatedUser)
        return "Password has been changed."
    }
}