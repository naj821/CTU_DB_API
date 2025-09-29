package com.kapston.CTU_DB_API.controller

import com.kapston.CTU_DB_API.domain.dto.request.RegisterRequest
import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import com.kapston.CTU_DB_API.service.abstraction.ProfileService
import com.kapston.CTU_DB_API.service.abstraction.UserService
import com.kapston.CTU_DB_API.utility.HashUtils.hashPassword
import com.kapston.CTU_DB_API.utility.JwtUtils
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val profileService: ProfileService,
    private val jwtUtils: JwtUtils
) {
    @PostMapping
    fun register(
        @CookieValue("jwt") jwt: String,
        @Valid @RequestBody user: RegisterRequest
    ): ResponseEntity<String> {
        jwtUtils.validateAccessToken(jwt)

        val hashPass = user.password.hashPassword()
        val hashedUser = RegisterRequest(email = user.email, password = hashPass, user.role)

        val userResponse = userService.create(hashedUser)

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse)
    }

    @GetMapping("/teachers")
    fun getAllTeachers(
        @CookieValue("jwt") jwt: String,
        ): ResponseEntity<List<ProfileEntity>> {

        jwtUtils.validateAccessToken(jwt)

        val teachers = profileService.getAllTeachers()
        return ResponseEntity.ok(teachers)
    }

    @PutMapping
    fun updateStatus(
        @CookieValue("jwt") jwt: String,
        @RequestParam(required = true) id: UUID
    ): ResponseEntity<String> {
        jwtUtils.validateAccessToken(jwt)
        val userResponse = userService.updateStatus(id)

        return ResponseEntity.status(HttpStatus.OK).body(userResponse)
    }

    @PostMapping("/reset-password")
    fun resetPassword(
        @CookieValue("jwt") jwt: String,
        @RequestParam(required = true) newPassword: String
    ): ResponseEntity<String> {
        jwtUtils.validateAccessToken(jwt)
        val stringUserId = jwtUtils.getUserIdFromToken(jwt)
        val userId = UUID.fromString(stringUserId)

        val hashedPassword = newPassword.hashPassword()

        val response = userService.resetPassword(userId, hashedPassword)

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}