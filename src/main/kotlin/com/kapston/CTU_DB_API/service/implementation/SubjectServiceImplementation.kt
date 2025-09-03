package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.CustomException.SubjectAlreadyExists
import com.kapston.CTU_DB_API.domain.dto.request.SubjectRequest
import com.kapston.CTU_DB_API.domain.dto.request.UpdateSubjectRequest
import com.kapston.CTU_DB_API.domain.dto.response.SubjectResponse
import com.kapston.CTU_DB_API.repository.SubjectRepository
import com.kapston.CTU_DB_API.service.abstraction.SubjectService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class SubjectServiceImplementation(
    private val subjectRepository: SubjectRepository
): SubjectService {
    override fun save(subjectRequest: SubjectRequest): String {
        val subjectExists = subjectRepository.existsBySubjectCodeOrName(
            subjectRequest.subjectCode,
            subjectRequest.name
        )
        if(subjectExists) throw SubjectAlreadyExists("A subject with the same name or code already exists.")
        subjectRepository.save(subjectRequest.toEntity())

        return "Subject created."
    }

    override fun update(updateSubjectRequest: UpdateSubjectRequest): String {
    val subjectExists = subjectRepository.existsBySubjectCodeOrName(
        updateSubjectRequest.subjectCode,
        updateSubjectRequest.name
    )
    if(subjectExists) throw SubjectAlreadyExists("A subject with the same name or code already exists.")
    subjectRepository.save(updateSubjectRequest.toEntity())

    return "Subject updated."
    }

    override fun search(subjectCode: String?, name: String?, page: Int, size: Int): Page<SubjectResponse> {
        val pageable = PageRequest.of(page, size)
        return subjectRepository.search(subjectCode, name, pageable)
    }
}