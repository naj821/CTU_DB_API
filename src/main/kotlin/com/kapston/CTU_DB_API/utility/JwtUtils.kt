package com.kapston.CTU_DB_API.utility

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import java.util.Base64
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.Date

@Service
class JwtUtils(
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.access-token.validity-seconds}") private val accessTokenValidityS: Long,
    @Value("\${jwt.refresh-token.validity-seconds}") private val refreshTokenValidityS: Long
) {
    private val secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))
    private val accessTokenValidityMs = accessTokenValidityS * 1000L
    val refreshTokenValidityMs = refreshTokenValidityS * 1000L

    private fun generateToken(
        userId: String,
        type: String,
        expiry: Long
    ): String {
        val now = Date()
        val expiryDate = Date(now.time + expiry)
        return Jwts.builder()
            .subject(userId)
            .claim("type", type)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun generateAccessToken(userId: String): String {
        return generateToken(userId, "access", accessTokenValidityMs)
    }

    fun generateRefreshToken(userId: String): String {
        return generateToken(userId, "refresh", refreshTokenValidityMs)
    }

    fun validateAccessToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "access"
    }

    fun validateRefreshToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "refresh"
    }

    fun getUserIdFromToken(token: String): String {
        val claims = parseAllClaims(token) ?: throw ResponseStatusException(
            HttpStatusCode.valueOf(401),
            "Invalid token."
        )
        return claims.subject
    }

    private fun parseAllClaims(token: String): Claims? {
        val rawToken = if (token.startsWith("Bearer ")) {
            token.removePrefix("Bearer ")
        } else token
        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken)
                .payload
        } catch (e: ExpiredJwtException) {
            return null
        }
    }
}