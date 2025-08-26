package com.kapston.CTU_DB_API.domain.dto.response

import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import java.util.UUID

data class SectionResponse(
    val id: UUID,
    val name: String,
    val gradeLevel: String,
    val adviser: ProfileEntity,
)
