package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.CustomException.UnauthorizedException
import com.kapston.CTU_DB_API.domain.entity.TokenEntity
import com.kapston.CTU_DB_API.repository.TokenRepository
import com.kapston.CTU_DB_API.service.abstraction.AuthenticationService
import com.kapston.CTU_DB_API.utility.JwtUtils
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthenticationServiceImplementation(
    private val jwtUtils: JwtUtils,
    private val tokenRepository: TokenRepository
): AuthenticationService {
    override fun saveTokens(token: TokenEntity): Unit {
        val doesExist = tokenRepository.existsByUserId(
            token.userId
        )

        if(doesExist) throw IllegalArgumentException("You are already logged in.")

        tokenRepository.save(token)
        return
    }

    override fun validateAccessToken(token: String): Unit {
        val validToken = jwtUtils.validateAccessToken(token)
        if(!validToken) throw IllegalArgumentException("Invalid token.2")
    }

    @Transactional
    override fun refresh(jwt: String): String {
        val tokenData = tokenRepository.findByHashedAccessToken(jwt)
        val refreshToken = tokenData?.hashedRefreshToken

        tokenData.takeIf { it != null }?.let { token ->
            val newAccessToken = jwtUtils.generateAccessToken(token.userId.toString())
            val newToken = token.copy(
                hashedAccessToken = newAccessToken
            )
            tokenRepository.save(newToken)
        }

        refreshToken?.let {
            if (!jwtUtils.validateRefreshToken(it)) {
                throw IllegalArgumentException("Refresh token expired. Please log in again.")
            }
        }

        val newAccessToken = jwtUtils.generateAccessToken(tokenData?.userId.toString())
        val newRefreshToken = jwtUtils.generateRefreshToken(tokenData?.userId.toString())

        val updatedToken = tokenData?.copy(
            hashedAccessToken = newAccessToken,
            hashedRefreshToken = newRefreshToken
        )

        updatedToken?.let {
            tokenRepository.save(it)
        }

        return newAccessToken
    }

    @Transactional
    override fun logout(jwt: String) {
        val stringId = jwtUtils.getUserIdFromToken(jwt)
        val userId = UUID.fromString(stringId)
        tokenRepository.deleteByUserId(userId)
    }
}