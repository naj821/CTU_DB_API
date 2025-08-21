package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.CustomException.ProfileNotFoundException
import com.kapston.CTU_DB_API.CustomException.UserNotFoundException
import com.kapston.CTU_DB_API.domain.Enums.Role
import com.kapston.CTU_DB_API.domain.dto.request.ProfileRequest
import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import com.kapston.CTU_DB_API.repository.ProfileRepository
import com.kapston.CTU_DB_API.service.abstraction.ProfileService
import org.springframework.dao.DataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Service
class ProfileServiceImplementation(
    private val profileRepository: ProfileRepository
): ProfileService {

    override fun saveOrUpdate(profileEntity: ProfileEntity): String {
        try {
            val existingProfile = profileRepository.findByUserEntity(profileEntity.userEntity)
            val updateProfile = if(existingProfile != null) {
                ProfileEntity(
                    id = existingProfile.id,
                    userEntity = existingProfile.userEntity,
                    firstName = profileEntity.firstName.takeIf { !it.isNotBlank() } ?: existingProfile.firstName,
                    middleName = profileEntity.middleName.takeIf { !it.isNullOrBlank() } ?: existingProfile.middleName,
                    lastName = profileEntity.lastName.takeIf { !it.isNotBlank() } ?: existingProfile.lastName,
                    gender = profileEntity.gender ?: existingProfile.gender,
                    birthDate = profileEntity.birthDate ?: existingProfile.birthDate,
                    contactNumber = profileEntity.contactNumber.takeIf { !it.isNullOrBlank() } ?: existingProfile.contactNumber,
                    address = profileEntity.address.takeIf { !it.isNullOrBlank() } ?: existingProfile.address,
                    createdAt = existingProfile.createdAt,
                    updatedAt = LocalDateTime.now(),
                )
            } else {
                profileEntity
            }
            profileRepository.save(updateProfile)

            return "Profile saved."
        } catch (e: DataAccessException) {
            throw RuntimeException("Failed to save profile.")
        }
    }

    override fun getProfile(id: UUID): ProfileEntity? {
        return profileRepository.findById(id)
            .orElseThrow { ProfileNotFoundException("No profile found.") }

    }

    override fun search(role: Role?, name: String?, page: Int, size: Int): Page<ProfileEntity> {
        val page = PageRequest.of(page, size)

        return profileRepository.search(role, name, page)
    }

    override fun findName(name: String): ProfileEntity? {
        return profileRepository.findByName(name)
            ?: throw UserNotFoundException("User with name $name not found.")
    }
}