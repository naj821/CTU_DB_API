package com.kapston.CTU_DB_API.domain.dto.request

import com.kapston.CTU_DB_API.domain.Enums.Role
import com.kapston.CTU_DB_API.domain.entity.UserEntity

data class RegisterRequest(
    val email: String,
    val password: String,
    val role: Role = Role.STUDENT
)

{
    fun toEntity(): UserEntity =
        UserEntity(
            email = email,
            password = password,
            role = role
        )
}
