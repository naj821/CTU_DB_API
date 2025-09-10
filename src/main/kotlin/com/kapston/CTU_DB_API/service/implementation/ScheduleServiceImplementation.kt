package com.kapston.CTU_DB_API.service.implementation

import com.kapston.CTU_DB_API.CustomException.ScheduleConflictError
import com.kapston.CTU_DB_API.CustomException.ScheduleSaveError
import com.kapston.CTU_DB_API.domain.dto.request.ScheduleRequest
import com.kapston.CTU_DB_API.repository.ScheduleRepository
import com.kapston.CTU_DB_API.service.abstraction.ScheduleService
import org.springframework.stereotype.Service

@Service
class ScheduleServiceImplementation(
    private val scheduleRepository: ScheduleRepository
): ScheduleService {
    override fun save(scheduleRequest: ScheduleRequest): String {
        try {
            val teacherId = scheduleRequest.teacher.id
                ?: throw ScheduleSaveError("Teacher ID is required to create a schedule.")

            val conflictExists = scheduleRepository.hasScheduleExists(
                teacherId,
                scheduleRequest.subject.name,
                scheduleRequest.section.name,
                scheduleRequest.startTime,
                scheduleRequest.endTime
            )

            if (conflictExists) {
                throw ScheduleConflictError("The schedule cannot be created because it conflicts with an existing entry.")
            }
            scheduleRepository.save(scheduleRequest.toEntity())

            return "Schedule added."
        } catch (e: ScheduleSaveError) {
            throw ScheduleSaveError("The schedule cannot be created because it conflicts with an existing entry.")
        }
    }
}