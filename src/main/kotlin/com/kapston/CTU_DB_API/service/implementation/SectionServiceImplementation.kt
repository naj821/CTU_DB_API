package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.CustomException.SectionAlreadyExistsException
import com.kapston.CTU_DB_API.domain.entity.SectionEntity
import com.kapston.CTU_DB_API.repository.SectionRepository
import com.kapston.CTU_DB_API.service.abstraction.SectionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
        pageable: Pageable
    ): Page<SectionEntity> {
       return sectionRepository.search(gradeLevel, name, adviserName, pageable)
    }

    override fun update(sectionEntity: SectionEntity): String {
        val section = sectionRepository.findByName(sectionEntity.name)
        return "TODO"

    }
}