package koriit.kotlin.slf4j.mdc.correlation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.slf4j.MDC

internal class CorrelationTest {

    @Test
    fun `should add correlation`() {
        val correlation1 = correlateThread()

        assertCorrelation(correlation1)

        val scope = CoroutineScope(correlated(correlation1))
        runBlocking {

            assertCorrelation(correlation1)

            scope.launch(correlated()) {
                val correlation2 = coroutineContext[CorrelationId]!!.value
                assertNotEquals(correlation1, correlation2)

                assertCorrelation(correlation2)

                withCorrelation {
                    val correlation3 = coroutineContext[CorrelationId]!!.value
                    assertNotEquals(correlation1, correlation3)
                    assertNotEquals(correlation2, correlation3)

                    assertCorrelation(correlation3)
                }

                assertCorrelation(correlation2)
            }
            assertCorrelation(correlation1)
        }

        assertCorrelation(correlation1)
        clearThreadCorrelation()
    }

    private fun assertCorrelation(correlationId: String) {
        assertEquals(correlationId, MDC.get(MDC_CORRELATION_KEY))
    }

}