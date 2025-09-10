package com.kapston.CTU_DB_API.controller

import com.kapston.CTU_DB_API.domain.dto.request.ScheduleRequest
import com.kapston.CTU_DB_API.service.abstraction.ScheduleService
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
@RequestMapping("/api/schedules")
class ScheduleController(
    private val scheduleService: ScheduleService,
    private val jwtUtils: JwtUtils
) {
    @PostMapping
    fun save(
        @CookieValue("jwt") jwt: String,
        @Valid @RequestBody(required = true) scheduleRequest: ScheduleRequest
    ): ResponseEntity<String> {
        jwtUtils.validateAccessToken(jwt)
        val scheduleResponse = scheduleService.save(scheduleRequest)

        return ResponseEntity.status(HttpStatus.OK).body(scheduleResponse)
    }
}