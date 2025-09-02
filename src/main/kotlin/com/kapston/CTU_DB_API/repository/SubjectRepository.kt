package com.kapston.CTU_DB_API.repository

import com.kapston.CTU_DB_API.domain.entity.SubjectEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SubjectRepository: JpaRepository<SubjectEntity, UUID> {
    fun existsBySubjectCodeOrName(subjectCode: String, name: String): Boolean
}