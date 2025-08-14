package com.kapston.CTU_DB_API.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "token")
class TokenEntity (
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_id_seq")
    @SequenceGenerator(name = "token_id_seq", sequenceName = "token_id_seq", allocationSize = 1)
    var id: Int? = null,
    @Column(name = "user_id")
    var userId: UUID? = null,
    @Column(name ="hashed_access_token")
    var hashedAccessToken: String = "",
    @Column(name ="hashed_refresh_token")
    var hashedRefreshToken: String = "",
    @Column(name = "created_at")
    @CreationTimestamp
    var createdAt: LocalDateTime? = null
)