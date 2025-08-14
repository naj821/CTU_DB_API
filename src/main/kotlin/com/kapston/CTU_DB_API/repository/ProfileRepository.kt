package com.kapston.CTU_DB_API.repository

import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProfileRepository: JpaRepository<ProfileEntity, UUID> {
    //TODO
}