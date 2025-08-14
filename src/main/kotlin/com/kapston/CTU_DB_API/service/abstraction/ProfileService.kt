package com.kapston.CTU_DB_API.service.abstraction

import com.kapston.CTU_DB_API.domain.dto.request.ProfileRequest
import com.kapston.CTU_DB_API.domain.entity.ProfileEntity

interface ProfileService {
    fun saveOrUpdate(profileEntity: ProfileEntity): String
}