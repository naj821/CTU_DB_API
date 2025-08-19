package com.kapston.CTU_DB_API.repository

import com.kapston.CTU_DB_API.domain.Enums.Role
import com.kapston.CTU_DB_API.domain.entity.ProfileEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ProfileRepository: JpaRepository<ProfileEntity, UUID> {
    @Query(
        """
    SELECT p
    FROM ProfileEntity p
    JOIN p.userEntity u
    WHERE (:role IS NULL OR u.role = :role)
      AND (
        :name IS NULL OR
        LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR
        LOWER(p.middleName) LIKE LOWER(CONCAT('%', :name, '%')) OR
        LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))
      )
    """
    )
    fun search(role: Role?, name: String?, pageable: Pageable): Page<ProfileEntity>
}