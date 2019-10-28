package koriit.kotlin.slf4j.mdc.correlation

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.slf4j.MDC
import kotlin.coroutines.Continuation

internal class ContinueCorrelationTest {

    @Test
    fun `should continue correlation`() = runBlocking(correlated()) {
        val ctx = coroutineContext
        val correlationId = coroutineContext[CorrelationId]!!.value
        assertCorrelation(correlationId, null)

        withSubCorrelation {
            val subCorrelationId = coroutineContext[SubCorrelationId]!!.value
            assertCorrelation(correlationId, subCorrelationId)

            val job = async(continueCorrelation(), start = CoroutineStart.LAZY) {
                assertCorrelation(correlationId, subCorrelationId)
            }

            launch(correlated()) {
                val correlationId2 = coroutineContext[CorrelationId]!!.value
                assertNotEquals(correlationId, correlationId2)

                assertCorrelation(correlationId2, null)

                withSubCorrelation {
                    val subCorrelationId2 = coroutineContext[SubCorrelationId]!!.value
                    assertNotEquals(subCorrelationId, subCorrelationId2)

                    assertCorrelation(correlationId2, subCorrelationId2)

                    continueCorrelation(ctx) {
                        assertCorrelation(correlationId, null)
                    }
                }

                continueCorrelation((job as Continuation<*>).context) {
                    assertCorrelation(correlationId, subCorrelationId)

                    job.await()

                    assertCorrelation(correlationId, subCorrelationId)
                }

                assertCorrelation(correlationId2, null)
            }.join()

            assertCorrelation(correlationId, subCorrelationId)
        }
        assertCorrelation(correlationId, null)
    }

    private fun assertCorrelation(correlationId: String, subCorrelationId: String?) {
        assertEquals(correlationId, MDC.get(MDC_CORRELATION_KEY))
        assertEquals(subCorrelationId, MDC.get(MDC_SUB_CORRELATION_KEY))
    }
}