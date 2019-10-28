package koriit.kotlin.slf4j.mdc.correlation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.slf4j.MDC

internal class SubCorrelationTest {

    @Test
    fun `should add sub correlation`() {
        val correlationId = correlateThread()

        fun assertCorrelation() {
            assertEquals(correlationId, MDC.get(MDC_CORRELATION_KEY))
        }
        assertCorrelation()
        assertSubCorrelation(null)

        val scope = CoroutineScope(subCorrelated())
        val correlation1 = scope.coroutineContext[SubCorrelationId]!!.value
        runBlocking {
            assertCorrelation()
            assertSubCorrelation(null)

            scope.launch(subCorrelated()) {
                val correlation2 = coroutineContext[SubCorrelationId]!!.value
                assertNotEquals(correlation1, correlation2)

                assertCorrelation()
                assertSubCorrelation(correlation2)

                withSubCorrelation {
                    val correlation3 = coroutineContext[SubCorrelationId]!!.value
                    assertNotEquals(correlation1, correlation3)
                    assertNotEquals(correlation2, correlation3)

                    assertCorrelation()
                    assertSubCorrelation(correlation3)
                }
                assertCorrelation()
                assertSubCorrelation(correlation2)
            }
            assertCorrelation()
            assertSubCorrelation(null)
        }
        assertCorrelation()
        assertSubCorrelation(null)
        clearThreadCorrelation()
    }

    private fun assertSubCorrelation(correlationId: String?) {
        assertEquals(correlationId, MDC.get(MDC_SUB_CORRELATION_KEY))
    }

}