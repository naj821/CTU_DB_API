package com.kapston.CTU_DB_API.domain.dto.response


import com.kapston.CTU_DB_API.domain.Enums.Role
import java.util.UUID

data class LoginResponse(val email: String, val role: Role, val id: UUID)


