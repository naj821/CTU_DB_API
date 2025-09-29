package com.kapston.CTU_DB_API.utility

import com.kapston.CTU_DB_API.CustomException.UnauthorizedException
import com.kapston.CTU_DB_API.service.abstraction.AuthenticationService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val authenticationService: AuthenticationService
) : OncePerRequestFilter() {

    private val publicEndpoints = listOf(
        "/api/auth/session",
        "/api/otp/verification",
        "/api/otp",
        "/api/users/reset-password",
        "/swagger-ui",
        "/v3/api-docs"
    )

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val uri = request.requestURI
        return publicEndpoints.any { uri.startsWith(it) }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = extractJwtFromCookie(request)

            jwt
                ?.takeIf { SecurityContextHolder.getContext().authentication == null }
                ?.let {
                    authenticationService.validateAccessToken(it)

                    val authToken = UsernamePasswordAuthenticationToken(
                        it,
                        null,
                        emptyList()
                    )

                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                    SecurityContextHolder.getContext().authentication = authToken
                }
        } catch (e: UnauthorizedException) {
            throw e
        }

        filterChain.doFilter(request, response)
    }

    private fun extractJwtFromCookie(request: HttpServletRequest): String? {
        return request.cookies?.find { it.name == "jwt" }?.value
    }
}
