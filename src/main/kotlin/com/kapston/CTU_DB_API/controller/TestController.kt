package com.kapston.CTU_DB_API.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class TestController {

    @GetMapping
    fun test(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body("Server is working!")
    }
}