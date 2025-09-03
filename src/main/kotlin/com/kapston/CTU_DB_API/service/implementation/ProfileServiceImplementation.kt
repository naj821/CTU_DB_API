package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.CustomException.ProfileNotFoundException
import com.kapston.CTU_DB_API.CustomException.UserNotFoundException
import com.kapston.CTU_DB_API.domain.Enums.Role
import com.kapston.CTU_DB_API.domain.dto.response.ProfileResponse
import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import com.kapston.CTU_DB_API.domain.entity.UserEntity
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

            val updatedProfile = existingProfile.let {
                it?.apply {
                    firstName = profileEntity.firstName
                    middleName = profileEntity.middleName
                    lastName = profileEntity.lastName
                    gender = profileEntity.gender
                    birthDate = profileEntity.birthDate
                    contactNumber = profileEntity.contactNumber
                    address = profileEntity.address
                }
            } ?: profileEntity

            profileRepository.save(updatedProfile)

            return "Profile saved."
        } catch (e: DataAccessException) {
            throw RuntimeException("Failed to save profile.")
        }
    }

    override fun getProfile(userEntity: UserEntity): ProfileResponse? {
        val userResponse = profileRepository.findByUserEntity(userEntity)
            ?: throw ProfileNotFoundException("Profile not found for user id=${userEntity.id}")

        return userResponse.toResponse()
    }

    override fun search(role: Role?, name: String?, page: Int, size: Int): Page<ProfileEntity> {
        val page = PageRequest.of(page, size)

        return profileRepository.search(role, name, page)
    }

    override fun findName(name: String): ProfileEntity? {
        return profileRepository.findByName(name)
            ?: throw UserNotFoundException("User with name $name not found.")
    }

    override fun getAllTeachers(): List<ProfileEntity> {
        return profileRepository.findAllTeachers()
    }
}