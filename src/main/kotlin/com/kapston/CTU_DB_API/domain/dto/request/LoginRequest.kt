package com.kapston.CTU_DB_API.domain.dto.request

import com.kapston.CTU_DB_API.domain.entity.UserEntity

data class LoginRequest(val email: String, val password: String)

{
    fun toEntity(): UserEntity = UserEntity(email = email, password = password)
}
