package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.domain.entity.TokenEntity
import com.kapston.CTU_DB_API.repository.TokenRepository
import com.kapston.CTU_DB_API.service.abstraction.AuthenticationService
import com.kapston.CTU_DB_API.utility.JwtUtils
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImplementation(
    private val jwtUtils: JwtUtils,
    private val tokenRepository: TokenRepository
): AuthenticationService {
    override fun saveTokens(token: TokenEntity): Unit {
        val doesExist = tokenRepository.existsByHashedAccessTokenAndHashedRefreshToken(
            hashedAccessToken = token.hashedAccessToken,
            hashedRefreshToken = token.hashedRefreshToken
        )

        if(doesExist) throw IllegalArgumentException("You are already logged in.")

        tokenRepository.saveAndFlush(token)
        return
    }

    override fun validateAccessToken(token: String): Unit {
        val validToken = jwtUtils.validateAccessToken(token)
        if(!validToken) throw IllegalArgumentException("Invalid token.2")
    }

    @Transactional
    override fun refresh(jwt: String): String {
        try {
            val session = tokenRepository.existsByHashedAccessToken(jwt)
            if(!session) throw IllegalArgumentException("Invalid token.")

            val tokenData = tokenRepository.findByHashedAccessToken(token = jwt)
            val accessToken = tokenData.hashedAccessToken
            val refreshToken = tokenData.hashedRefreshToken

            val validAccessToken = jwtUtils.validateAccessToken(accessToken)
            if(validAccessToken) return accessToken

            val validRefreshToken = jwtUtils.validateRefreshToken(refreshToken)
            if(!validRefreshToken) throw IllegalArgumentException("You are not logged in.")

            val newAccessToken = jwtUtils.generateAccessToken(tokenData.userId.toString())
            val tokenEntity = TokenEntity(
                id = tokenData.id,
                userId = tokenData.userId,
                hashedAccessToken = newAccessToken,
                hashedRefreshToken = refreshToken,
                createdAt = tokenData.createdAt
            )
            saveTokens(tokenEntity)

            return newAccessToken
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message)
        }
    }
}