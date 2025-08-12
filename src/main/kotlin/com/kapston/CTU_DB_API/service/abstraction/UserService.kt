package com.kapston.CTU_DB_API.service.abstraction

import com.kapston.CTU_DB_API.domain.dto.request.LoginRequest
import com.kapston.CTU_DB_API.domain.dto.request.RegisterRequest
import com.kapston.CTU_DB_API.domain.dto.response.LoginResponse

interface UserService {
    fun create(user: RegisterRequest): String
    fun authenticate(user: LoginRequest): LoginResponse
}