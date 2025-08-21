package com.kapston.CTU_DB_API.repository

import com.kapston.CTU_DB_API.domain.entity.SectionEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface SectionRepository: JpaRepository<SectionEntity, UUID> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): SectionEntity?

    @Query(
        """
    SELECT s
    FROM SectionEntity s
    JOIN s.adviser p
    WHERE 
      (:gradeLevel IS NULL OR s.gradeLevel = :gradeLevel)
      AND (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))
      AND (
        :adviserName IS NULL OR
        LOWER(p.firstName) LIKE LOWER(CONCAT('%', :adviserName, '%')) OR
        LOWER(p.middleName) LIKE LOWER(CONCAT('%', :adviserName, '%')) OR
        LOWER(p.lastName) LIKE LOWER(CONCAT('%', :adviserName, '%'))
      )
    """
    )
    fun search(
        @Param("gradeLevel") gradeLevel: String?,
        @Param("name") name: String?,
        @Param("adviserName") adviserName: String?,
        pageable: Pageable
    ): Page<SectionEntity>
}