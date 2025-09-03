package com.kapston.CTU_DB_API.controller

import com.kapston.CTU_DB_API.domain.dto.request.SubjectRequest
import com.kapston.CTU_DB_API.domain.dto.request.UpdateSubjectRequest
import com.kapston.CTU_DB_API.domain.dto.response.SubjectResponse
import com.kapston.CTU_DB_API.service.abstraction.SubjectService
import com.kapston.CTU_DB_API.utility.JwtUtils
import jakarta.validation.Valid
import org.springframework.data.domain.Page
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

    @PutMapping
    fun update(
        @CookieValue("jwt") jwt: String,
        @Valid @RequestBody updateSubjectRequest: UpdateSubjectRequest
    ): ResponseEntity<String> {
        jwtUtils.validateAccessToken(jwt)
        val subjectResponse = subjectService.update(updateSubjectRequest)

        return ResponseEntity.status(HttpStatus.CREATED).body(subjectResponse)
    }

    @GetMapping
    fun search(
        @CookieValue("jwt") jwt: String,
        @RequestParam(required = false) subjectCode: String?,
        @RequestParam(required = false) name: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): Page<SubjectResponse> {
        jwtUtils.validateAccessToken(jwt)

        return subjectService.search(subjectCode, name, page, size)
    }
}