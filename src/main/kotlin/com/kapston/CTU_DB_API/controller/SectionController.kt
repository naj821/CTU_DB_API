package com.kapston.CTU_DB_API.controller

import com.kapston.CTU_DB_API.domain.dto.request.CreateSectionRequest
import com.kapston.CTU_DB_API.service.abstraction.ProfileService
import com.kapston.CTU_DB_API.service.abstraction.SectionService
import com.kapston.CTU_DB_API.service.abstraction.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sections")
class SectionController(
    private val sectionService: SectionService,
    private val userService: UserService,
    private val profileService: ProfileService
) {

    @PostMapping
    fun create(
        @CookieValue("jwt") jwt: String,
        @Valid @RequestBody sectionRequest: CreateSectionRequest
    ): ResponseEntity<String> {
        val user = profileService.findName(sectionRequest.adviser)
        val response = user?.let { sectionService.create(sectionRequest.toEntity(it)) }

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}