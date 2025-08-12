package com.kapston.CTU_DB_API.CustomException

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleUserAlreadyExists(ex: Exception): ResponseEntity<GlobalExceptionModel> {
        val response = GlobalExceptionModel(
            ex.message
        )

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleInvalidCredentials(ex: BadCredentialsException): ResponseEntity<GlobalExceptionModel> {
        val response = GlobalExceptionModel(
            ex.message
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }
}