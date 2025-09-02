package com.kapston.CTU_DB_API.service.abstraction

import com.kapston.CTU_DB_API.domain.dto.request.SubjectRequest

interface SubjectService {
    fun save(subjectRequest: SubjectRequest): String
}