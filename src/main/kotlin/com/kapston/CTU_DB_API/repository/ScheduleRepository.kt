package com.kapston.CTU_DB_API.repository

import com.kapston.CTU_DB_API.domain.entity.ScheduleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.UUID

interface ScheduleRepository: JpaRepository<ScheduleEntity, UUID> {
    @Query(
        """
    SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END 
    FROM ScheduleEntity s 
    WHERE (s.teacher.id = :teacherId OR s.subject.name = :subjectName OR s.section.name = :sectionName)
      AND (s.startTime < :endTime AND s.endTime > :startTime)
    """
    )
    fun hasScheduleExists(
        teacherId: UUID,
        subjectName: String,
        sectionName: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ): Boolean
}