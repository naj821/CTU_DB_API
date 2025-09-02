package com.kapston.CTU_DB_API.controller

import com.kapston.CTU_DB_API.domain.dto.request.SubjectRequest
import com.kapston.CTU_DB_API.service.abstraction.SubjectService
import com.kapston.CTU_DB_API.utility.JwtUtils
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/subjects")
class SubjectController(
    private val subjectService: SubjectService,
    private val jwtUtils: JwtUtils
) {

    @PostMapping
    fun save(
        @CookieValue("jwt") jwt: String,
        @Valid @RequestBody subjectRequest: SubjectRequest
    ): ResponseEntity<String> {
        jwtUtils.validateAccessToken(jwt)
        val subjectResponse = subjectService.save(subjectRequest)

        return ResponseEntity.status(HttpStatus.CREATED).body(subjectResponse)
    }
}