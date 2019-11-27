package koriit.kotlin.slf4j.mdc.correlation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.slf4j.MDC

internal class ThreadCorrelationTest {

    @Test
    fun `should add thread correlation`() {
        assertCorrelation(null, null)

        val correlationId = correlateThread()
        assertCorrelation(correlationId, null)

        val subCorrelationId = subCorrelateThread(correlationId)
        assertCorrelation(correlationId, subCorrelationId)

        clearThreadCorrelation()
        assertCorrelation(null, null)
    }

    private fun assertCorrelation(correlationId: String?, subCorrelationId: String?) {
        assertEquals(correlationId, MDC.get(MDC_CORRELATION_KEY))
        assertEquals(subCorrelationId, MDC.get(MDC_SUB_CORRELATION_KEY))
    }
}
