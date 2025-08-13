package com.kapston.CTU_DB_API.domain.dto.response

import com.kapston.CTU_DB_API.domain.dto.request.TokenRequest

data class LoginResponse(val userResponse: UserResponse, val authorization: TokenRequest)


