package com.kapston.CTU_DB_API.service.abstraction

import com.kapston.CTU_DB_API.domain.Enums.StatusEnum
import com.kapston.CTU_DB_API.domain.dto.request.LoginRequest
import com.kapston.CTU_DB_API.domain.dto.request.RegisterRequest
import com.kapston.CTU_DB_API.domain.dto.response.LoginResponse
import com.kapston.CTU_DB_API.domain.entity.UserEntity
import java.util.UUID

interface UserService {
    fun create(user: RegisterRequest): String
    fun authenticate(user: LoginRequest): LoginResponse
    fun getUserEntity(id: UUID): UserEntity
    fun updateStatus(id: UUID): String
    fun resetPassword(id: UUID, newPassword: String): String
}