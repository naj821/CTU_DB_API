package com.kapston.CTU_DB_API.controller

import com.kapston.CTU_DB_API.domain.dto.request.ProfileRequest
import com.kapston.CTU_DB_API.service.abstraction.ProfileService
import com.kapston.CTU_DB_API.service.abstraction.UserService
import com.kapston.CTU_DB_API.utility.JwtUtils
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
@RequestMapping("/api/users")
class ProfileController(
    private val profileService: ProfileService,
    private val userService: UserService,
    private val jwtUtils: JwtUtils
) {
    @PostMapping("/profile")
    fun saveOrUpdate(
        @Valid @RequestBody profileRequest: ProfileRequest,
        @CookieValue("jwt") jwt: String
    ): ResponseEntity<String> {

        val stringId = jwtUtils.getUserIdFromToken(jwt)
        val userId = UUID.fromString(stringId)

        val user = userService.getUserEntity(userId)
        val profileResponse = profileService.saveOrUpdate(profileRequest.toEntity(user))

        return ResponseEntity.status(HttpStatus.CREATED).body(profileResponse)
    }
}