package com.kapston.CTU_DB_API.domain.dto.request

import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import com.kapston.CTU_DB_API.domain.entity.SectionEntity
import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

data class UpdateSectionRequest(
    val id: UUID,
    val name: String,
    val gradeLevel: String,
    val adviser: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

{
    fun toEntity(adviser: ProfileEntity): SectionEntity = SectionEntity(
        name,
        gradeLevel,
        adviser,
        id,
        createdAt,
        updatedAt
    )
}

