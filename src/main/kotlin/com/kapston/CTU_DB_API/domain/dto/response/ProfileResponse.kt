package com.kapston.CTU_DB_API.domain.dto.response

import com.kapston.CTU_DB_API.domain.Enums.Gender
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class ProfileResponse(
    val id: UUID?,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val gender: Gender?,
    val birthDate: LocalDate?,
    val contactNumber: String?,
    val address: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
