package com.kapston.CTU_DB_API.domain.dto.request

import java.time.Instant
import java.util.UUID

data class UpdateSectionRequest(
    val id: UUID,
    val name: String,
    val gradeLevel: String,
    val adviser: String,
    val createdAt: Instant?,
    val updatedAt: Instant = Instant.now()
)

