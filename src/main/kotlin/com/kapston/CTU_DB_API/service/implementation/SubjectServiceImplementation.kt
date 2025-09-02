package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.CustomException.SubjectAlreadyExists
import com.kapston.CTU_DB_API.domain.dto.request.SubjectRequest
import com.kapston.CTU_DB_API.repository.SubjectRepository
import com.kapston.CTU_DB_API.service.abstraction.SubjectService
import org.springframework.stereotype.Service

@Service
class SubjectServiceImplementation(
    private val subjectRepository: SubjectRepository
): SubjectService {
    override fun save(subjectRequest: SubjectRequest): String {
        val subjectExists = subjectRepository.existsBySubjectCodeOrName(subjectRequest.subjectCode, subjectRequest.name)
        if(subjectExists) throw SubjectAlreadyExists("A subject with the same name or code already exists.")
        subjectRepository.save(subjectRequest.toEntity())

        return "Subject created."
    }
}