package com.kapston.CTU_DB_API.controller

import com.kapston.CTU_DB_API.domain.dto.request.LoginRequest
import com.kapston.CTU_DB_API.domain.dto.response.LoginResponse
import com.kapston.CTU_DB_API.service.abstraction.UserService
import com.kapston.CTU_DB_API.service.implementation.AuthenticationServiceImplementation
import com.kapston.CTU_DB_API.utility.CookieUtils
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val cookieUtils: CookieUtils,
    private val authenticationServiceImplementation: AuthenticationServiceImplementation
) {
    @PostMapping("/session")
    fun login(@Valid @RequestBody user: LoginRequest, response: HttpServletResponse): ResponseEntity<LoginResponse> {
        val userResponse = userService.authenticate(user)

        val accessToken = userResponse.authorization.hashedAccessToken

        val cookie = cookieUtils.createJwtCookie(accessToken)
        response.addCookie(cookie)

        return ResponseEntity.status(HttpStatus.OK).body(userResponse)
    }

    @PostMapping("/refresh")
    fun refresh(
        @CookieValue("jwt") jwt: String
    ): ResponseEntity<String> {
        val response = authenticationServiceImplementation.refresh(jwt)

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}