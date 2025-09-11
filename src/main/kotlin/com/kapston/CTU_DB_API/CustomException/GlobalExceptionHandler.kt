package com.kapston.CTU_DB_API.CustomException

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExists(ex: UserAlreadyExistsException): ResponseEntity<GlobalExceptionModel> {
        val response = GlobalExceptionModel(
            ex.message ?: "Unexpected runtime error"
        )

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleInvalidCredentials(ex: BadCredentialsException): ResponseEntity<GlobalExceptionModel> {
        val response = GlobalExceptionModel(
            ex.message ?: "Unexpected runtime error"
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleRuntimeException(ex: IllegalArgumentException): ResponseEntity<GlobalExceptionModel> {
        val response = GlobalExceptionModel(
            ex.message ?: "Unexpected runtime error"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }

    @ExceptionHandler(SectionAlreadyExistsException::class)
    fun handleSectionAlreadyExistsException(ex: SectionAlreadyExistsException): ResponseEntity<GlobalExceptionModel> {
        val response = GlobalExceptionModel(
            ex.message ?: "Unexpected runtime error"
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response)
    }

    @ExceptionHandler(ProfileNotFoundException::class)
    fun handleProfileNotFoundException(ex: ProfileNotFoundException): ResponseEntity<GlobalExceptionModel> {
        val response = GlobalExceptionModel(
            ex.message ?: "Unexpected runtime error"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(ScheduleConflictError::class)
    fun handleScheduleConflict(ex: ScheduleConflictError): ResponseEntity<GlobalExceptionModel> {
        val response = GlobalExceptionModel(
            ex.message ?: "Unexpected runtime error"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }

    @ExceptionHandler(ScheduleSaveError::class)
    fun handleInternalServerException(ex: ScheduleSaveError): ResponseEntity<GlobalExceptionModel> {
        val response = GlobalExceptionModel(
            ex.message ?: "Unexpected runtime error"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }

    @ExceptionHandler(SectionNotFoundException::class)
    fun handleSubjectNotFoundException(ex: SectionNotFoundException): ResponseEntity<GlobalExceptionModel> {
        val response = GlobalExceptionModel(
            ex.message ?: "Unexpected runtime error"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<GlobalExceptionModel> {
        val response = GlobalExceptionModel(
            ex.message ?: "Unexpected runtime error"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }
}