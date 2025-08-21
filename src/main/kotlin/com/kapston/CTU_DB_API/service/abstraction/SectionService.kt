package com.kapston.CTU_DB_API.service.abstraction

import com.kapston.CTU_DB_API.domain.entity.SectionEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SectionService {
    fun create(sectionEntity: SectionEntity): String
    fun update(sectionEntity: SectionEntity): String
    fun search(
        gradeLevel: String?,
        name: String?,
        adviserName: String?,
        pageable: Pageable
    ): Page<SectionEntity>
}