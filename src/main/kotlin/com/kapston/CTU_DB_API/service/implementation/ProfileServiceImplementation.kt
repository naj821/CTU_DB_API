package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.domain.dto.request.ProfileRequest
import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import com.kapston.CTU_DB_API.repository.ProfileRepository
import com.kapston.CTU_DB_API.service.abstraction.ProfileService
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service

@Service
class ProfileServiceImplementation(
    private val profileRepository: ProfileRepository
): ProfileService {

    override fun saveOrUpdate(profileEntity: ProfileEntity): String {
        try {
            profileRepository.save(profileEntity)

            return "Profile saved."
        } catch (e: DataAccessException) {
            throw RuntimeException("Failed to save profile.")
        }
    }
}