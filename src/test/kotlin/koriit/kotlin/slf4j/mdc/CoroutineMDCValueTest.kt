package koriit.kotlin.slf4j.mdc

import koriit.kotlin.slf4j.mdc.correlation.SubCorrelationId
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.slf4j.MDC
import kotlin.coroutines.CoroutineContext

internal class CoroutineMDCValueTest {

    class TestValue() : CoroutineMDCValue("TestValue", "TestValue", TestValue) {
        companion object Key : CoroutineContext.Key<SubCorrelationId>
    }

    @Test
    fun `should put value into coroutine MDC`() {
        assertEquals(null, MDC.get("TestValue"))

        runBlocking(TestValue()) {
            assertEquals("TestValue", MDC.get("TestValue"))
        }

        runBlocking {
            assertEquals(null, MDC.get("TestValue"))
            launch(TestValue()) {
                assertEquals("TestValue", MDC.get("TestValue"))
            }.join()
            assertEquals(null, MDC.get("TestValue"))
        }

        assertEquals(null, MDC.get("TestValue"))
    }
}