package com.kapston.CTU_DB_API

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import io.github.cdimascio.dotenv.dotenv

@SpringBootApplication
class CtuDbApiApplication

fun main(args: Array<String>) {
	val dotenv = dotenv {
		ignoreIfMissing = true
		directory = "."
	}
	dotenv.entries().forEach {
		System.setProperty(it.key, it.value)
	}

	runApplication<CtuDbApiApplication>(*args)
}
