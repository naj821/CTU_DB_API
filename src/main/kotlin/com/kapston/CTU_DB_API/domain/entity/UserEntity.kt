package com.kapston.CTU_DB_API.domain.entity

import com.kapston.CTU_DB_API.domain.Enums.Role
import com.kapston.CTU_DB_API.domain.dto.response.UserResponse
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
class UserEntity(

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false)
    val password: String = "",

    @Column(name = "membership_code", insertable = false, updatable = false)
    val membershipCode: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role = Role.STUDENT,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime? = null
)

{
    fun toResponse(): UserResponse = UserResponse(
        email = email,
        membershipCode = membershipCode!!,
        role = role,
        id = id!!
    )
}
