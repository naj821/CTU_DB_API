package com.kapston.CTU_DB_API.domain.dto.request

import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import com.kapston.CTU_DB_API.domain.entity.ScheduleEntity
import com.kapston.CTU_DB_API.domain.entity.SectionEntity
import com.kapston.CTU_DB_API.domain.entity.SubjectEntity
import java.time.LocalDateTime

data class ScheduleRequest(
    val teacher: ProfileEntity,
    val subject: SubjectEntity,
    val section: SectionEntity,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

{
    fun toEntity(): ScheduleEntity = ScheduleEntity(
        teacher = teacher,
        subject = subject,
        section = section,
        startTime = startTime,
        endTime = endTime,
        createdAt = createdAt
    )
}
