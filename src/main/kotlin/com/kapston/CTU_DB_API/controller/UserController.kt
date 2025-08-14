package com.kapston.CTU_DB_API.controller

import com.kapston.CTU_DB_API.domain.dto.request.LoginRequest
import com.kapston.CTU_DB_API.domain.dto.request.RegisterRequest
import com.kapston.CTU_DB_API.domain.dto.response.LoginResponse
import com.kapston.CTU_DB_API.service.abstraction.UserService
import com.kapston.CTU_DB_API.utility.CookieUtils
import com.kapston.CTU_DB_API.utility.HashUtils.hashPassword
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class UserController(
    private val userService: UserService,
    private val cookieUtils: CookieUtils
) {
    @PostMapping("/users")
    fun register(@Valid @RequestBody user: RegisterRequest): ResponseEntity<String> {
        val hashPass = user.password.hashPassword()
        val hashedUser = RegisterRequest(email = user.email, password = hashPass, user.role)

        val userResponse = userService.create(hashedUser)

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse)
    }

    @PostMapping("/auth/session")
    fun login(@Valid @RequestBody user: LoginRequest, response: HttpServletResponse): ResponseEntity<LoginResponse> {
        val userResponse = userService.authenticate(user)

        val accessToken = userResponse.authorization.hashedAccessToken

        val cookie = cookieUtils.createJwtCookie(accessToken)
        response.addCookie(cookie)

        return ResponseEntity.status(HttpStatus.OK).body(userResponse)
    }


}