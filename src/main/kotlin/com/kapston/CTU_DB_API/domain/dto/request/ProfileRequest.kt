package com.kapston.CTU_DB_API.domain.dto.request

import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import com.kapston.CTU_DB_API.domain.Enums.Gender
import com.kapston.CTU_DB_API.domain.entity.UserEntity
import java.time.LocalDate

data class ProfileRequest(
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
    val gender: Gender? = null,
    val birthDate: LocalDate? = null,
    val contactNumber: String? = null,
    val address: String? = null,
)

{
    fun toEntity(userEntity: UserEntity): ProfileEntity = ProfileEntity(
        firstName,
        middleName,
        lastName,
        gender,
        birthDate,
        contactNumber,
        address,
        userEntity = userEntity
    )
}
