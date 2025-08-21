package com.kapston.CTU_DB_API.domain.dto.request

import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import com.kapston.CTU_DB_API.domain.entity.SectionEntity

data class CreateSectionRequest(
    val name: String,
    val gradeLevel: String,
    val adviser: String
)

{
    fun toEntity(adv: ProfileEntity): SectionEntity = SectionEntity(
        name,
        gradeLevel,
        adviser = adv
    )
}

