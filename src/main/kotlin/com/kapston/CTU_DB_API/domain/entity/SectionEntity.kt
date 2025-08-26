package com.kapston.CTU_DB_API.domain.entity

import com.kapston.CTU_DB_API.domain.dto.response.SectionResponse
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "sections")
class SectionEntity(

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(name = "grade_level", nullable = false)
    val gradeLevel: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adviser_id", nullable = false, unique = true)
    val adviser: ProfileEntity,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime? = null
)

{
    fun toResponse(): SectionResponse = SectionResponse(
        id = id!!,
        name,
        gradeLevel,
        adviser = adviser
    )
}
