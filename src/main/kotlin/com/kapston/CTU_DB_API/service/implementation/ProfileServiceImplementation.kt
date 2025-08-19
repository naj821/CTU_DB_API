package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.domain.Enums.Role
import com.kapston.CTU_DB_API.domain.dto.request.ProfileRequest
import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import com.kapston.CTU_DB_API.repository.ProfileRepository
import com.kapston.CTU_DB_API.service.abstraction.ProfileService
import org.springframework.dao.DataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ProfileServiceImplementation(
    private val profileRepository: ProfileRepository
): ProfileService {

    override fun saveOrUpdate(profileEntity: ProfileEntity): String {
        try {
            val existingProfile = profileRepository.findByUserEntity(profileEntity.userEntity)
            val updateProfile = existingProfile?.apply {
                firstName = profileEntity.firstName
                middleName = profileEntity.middleName
                lastName = profileEntity.lastName
                gender = profileEntity.gender
                birthDate = profileEntity.birthDate
                contactNumber = profileEntity.contactNumber
                address = profileEntity.address
            } ?: profileEntity
            profileRepository.save(updateProfile)

            return "Profile saved."
        } catch (e: DataAccessException) {
            throw RuntimeException("Failed to save profile.")
        }
    }

    override fun search(role: Role?, name: String?, page: Int, size: Int): Page<ProfileEntity> {
        val page = PageRequest.of(page, size)

        return profileRepository.search(role, name, page)
    }
}