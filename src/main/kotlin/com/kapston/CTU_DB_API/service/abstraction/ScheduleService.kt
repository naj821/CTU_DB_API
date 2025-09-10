package com.kapston.CTU_DB_API.service.abstraction

import com.kapston.CTU_DB_API.domain.dto.request.ScheduleRequest

interface ScheduleService {
    fun save(scheduleRequest: ScheduleRequest): String
}