package com.kapston.CTU_DB_API.controller

import com.kapston.CTU_DB_API.domain.Enums.Role
import com.kapston.CTU_DB_API.domain.dto.request.ProfileRequest
import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import com.kapston.CTU_DB_API.service.abstraction.ProfileService
import com.kapston.CTU_DB_API.service.abstraction.UserService
import com.kapston.CTU_DB_API.service.implementation.AuthenticationServiceImplementation
import com.kapston.CTU_DB_API.utility.JwtUtils
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
@RequestMapping("/api/users")
class ProfileController(
    private val profileService: ProfileService,
    private val userService: UserService,
    private val authenticationServiceImplementation: AuthenticationServiceImplementation,
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

    @GetMapping("/profiles")
    fun searchProfiles(
        @RequestParam(required = false) role: Role?,
        @RequestParam(required = false) name: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @CookieValue("jwt") jwt: String
    ): Page<ProfileEntity> {
        authenticationServiceImplementation.validateAccessToken(jwt)

        return profileService.search(
            role,
            name,
            page,
            size
        )
    }
}