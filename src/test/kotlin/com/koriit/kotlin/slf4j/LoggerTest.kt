package com.koriit.kotlin.slf4j

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.slf4j.Logger

private val topLogger = logger {}

private fun topFunctionLogger(): Logger {
    return logger {}
}

class LoggerTest {

    private val classLogger = logger {}

    @Test
    fun `should compute full logger name`() {
        val functionLogger = logger {}

        assertEquals("com.koriit.kotlin.slf4j.LoggerTestKt", topLogger.name)
        assertEquals("com.koriit.kotlin.slf4j.LoggerTestKt", topFunctionLogger().name)
        assertEquals("com.koriit.kotlin.slf4j.LoggerTest", classLogger.name)
        assertEquals("com.koriit.kotlin.slf4j.LoggerTest", functionLogger.name)
    }
}
