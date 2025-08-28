package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.CustomException.SectionAlreadyExistsException
import com.kapston.CTU_DB_API.CustomException.SectionNotFoundException
import com.kapston.CTU_DB_API.domain.dto.response.SectionResponse
import com.kapston.CTU_DB_API.domain.entity.SectionEntity
import com.kapston.CTU_DB_API.repository.SectionRepository
import com.kapston.CTU_DB_API.service.abstraction.SectionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class SectionServiceImplementation(
    private val sectionRepository: SectionRepository
): SectionService {
    override fun create(sectionEntity: SectionEntity): String {
        val sectionExists = sectionRepository.existsByName(sectionEntity.name)
        if (sectionExists)
            throw SectionAlreadyExistsException("Section '${sectionEntity.name}' already exists.")

        sectionRepository.save(sectionEntity)
        return "Section saved."
    }

    override fun search(
        gradeLevel: String?,
        name: String?,
        adviserName: String?,
        page: Int,
        size: Int
    ): Page<SectionResponse> {
        val pageable = PageRequest.of(page, size)
        return sectionRepository.search(gradeLevel, name, adviserName, pageable)
    }

    override fun update(sectionEntity: SectionEntity): String {
        val section = sectionRepository.findById(sectionEntity.id!!)
            .orElseThrow { SectionNotFoundException("Section not found with id: ${sectionEntity.id}") }

        sectionRepository.findByName(sectionEntity.name)?.let { existing ->
            if (existing.id != section.id) {
                throw SectionAlreadyExistsException("Duplicate ${sectionEntity.name}.")
            }
        }

        section.apply {
            name = sectionEntity.name
            gradeLevel = sectionEntity.gradeLevel
            adviser = sectionEntity.adviser
        }

        sectionRepository.save(section)
        return "Section edited."
    }
}