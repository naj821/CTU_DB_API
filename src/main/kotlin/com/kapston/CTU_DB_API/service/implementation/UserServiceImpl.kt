package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.domain.dto.request.LoginRequest
import com.kapston.CTU_DB_API.domain.dto.request.RegisterRequest
import com.kapston.CTU_DB_API.domain.dto.response.LoginResponse
import com.kapston.CTU_DB_API.repository.UserRepository
import com.kapston.CTU_DB_API.service.abstraction.UserService
import com.kapston.CTU_DB_API.utility.HashUtils.verifyPassword
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepo: UserRepository): UserService {
    override fun create(user: RegisterRequest): String {

        val userExists = userRepo.existsByEmail(user.email)

        if(userExists) throw Exception("Email already exists.")

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

        return LoginResponse(
            email = authUser.email,
            role = authUser.role,
            id = authUser.id!!
        )

    }
}