package com.kapston.CTU_DB_API.service.abstraction

import com.kapston.CTU_DB_API.domain.entity.TokenEntity

interface AuthenticationService {
    fun saveTokens(token: TokenEntity): Unit
    fun validateAccessToken(token: String): Unit
    fun refresh(jwt: String): String
    fun logout(jwt: String): Unit
}