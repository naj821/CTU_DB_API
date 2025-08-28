package com.kapston.CTU_DB_API.controller

import com.kapston.CTU_DB_API.domain.dto.request.RegisterRequest
import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import com.kapston.CTU_DB_API.service.abstraction.ProfileService
import com.kapston.CTU_DB_API.service.abstraction.UserService
import com.kapston.CTU_DB_API.utility.HashUtils.hashPassword
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class UserController(
    private val userService: UserService,
    private val profileService: ProfileService
) {
    @PostMapping("/users")
    fun register(@Valid @RequestBody user: RegisterRequest): ResponseEntity<String> {
        val hashPass = user.password.hashPassword()
        val hashedUser = RegisterRequest(email = user.email, password = hashPass, user.role)

        val userResponse = userService.create(hashedUser)

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse)
    }

    @GetMapping("/teachers")
    fun getAllTeachers(): ResponseEntity<List<ProfileEntity>> {
        val teachers = profileService.getAllTeachers()
        return ResponseEntity.ok(teachers)
    }
}