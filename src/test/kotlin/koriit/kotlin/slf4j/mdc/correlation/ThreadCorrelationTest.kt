package koriit.kotlin.slf4j.mdc.correlation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ThreadCorrelationTest {

    @Test
    fun `should add thread correlation`() {
        assertCorrelation(null, null)

        val correlationId = correlateThread()
        assertCorrelation(correlationId, null)

        val subCorrelationId = subCorrelateThread(correlationId)
        assertCorrelation(correlationId, subCorrelationId)

        clearThreadCorrelation()
    }

    @Test
    fun `should clear thread correlation`() {
        val correlationId = correlateThread()
        val subCorrelationId = subCorrelateThread()

        assertCorrelation(correlationId, subCorrelationId)

        clearThreadSubCorrelation()
        assertCorrelation(correlationId, null)

        subCorrelateThread()
        correlateThread()
        assertSubCorrelation(null)

        subCorrelateThread()
        clearThreadCorrelation()
        assertCorrelation(null, null)
    }

    @Test
    fun `should return correlation id`() {
        val myCorrelation = correlateThread()
        val mySubCorrelation = subCorrelateThread()

        assertEquals(myCorrelation, correlationId)
        assertEquals(mySubCorrelation, subCorrelationId)

        clearThreadCorrelation()
    }
}
