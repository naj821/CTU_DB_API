package com.kapston.CTU_DB_API.service.abstraction

import com.kapston.CTU_DB_API.domain.Enums.Role
import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import org.springframework.data.domain.Page
import java.util.UUID

interface ProfileService {
    fun saveOrUpdate(profileEntity: ProfileEntity): String
    fun getProfile(id: UUID): ProfileEntity?
    fun search(role: Role?, name: String?, page: Int, size: Int): Page<ProfileEntity>
    fun findName(name: String): ProfileEntity?
}