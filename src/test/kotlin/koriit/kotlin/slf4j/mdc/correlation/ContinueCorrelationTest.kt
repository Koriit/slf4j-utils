package koriit.kotlin.slf4j.mdc.correlation

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import kotlin.coroutines.Continuation

internal class ContinueCorrelationTest {

    @Test
    fun `should continue correlation`() = runBlocking(correlated()) {
        val ctx = coroutineContext
        val correlationId = coroutineContext[CorrelationId]!!.value

        // assert thread correlation
        assertCorrelation(correlationId, null)

        // should generate new sub-correlation id
        withSubCorrelation {
            val subCorrelationId = coroutineContext[SubCorrelationId]!!.value
            assertNotEquals(correlationId, subCorrelationId)
            assertCorrelation(correlationId, subCorrelationId)

            // create lazy job with some correlation
            val job = async(continueCorrelation(), start = CoroutineStart.LAZY) {
                assertCorrelation(correlationId, subCorrelationId)
            }

            // should generate new correlation id and clear sub-correlation
            launch(correlated()) {
                val correlationId2 = coroutineContext[CorrelationId]!!.value
                assertCorrelation(correlationId2, null)
                assertNotEquals(correlationId, correlationId2)

                // should generate new sub-correlation id
                withSubCorrelation {
                    val subCorrelationId2 = coroutineContext[SubCorrelationId]!!.value
                    assertCorrelation(correlationId2, subCorrelationId2)
                    assertNotEquals(subCorrelationId, subCorrelationId2)

                    // should have the same correlation as passed context
                    continueCorrelation(ctx) {
                        assertCorrelation(correlationId, null)
                    }
                }

                // should have the same correlation as the job
                continueCorrelation((job as Continuation<*>).context) {
                    assertCorrelation(correlationId, subCorrelationId)

                    job.await()

                    // should retain correlation
                    assertCorrelation(correlationId, subCorrelationId)
                }

                // should retain correlation
                assertCorrelation(correlationId2, null)
            }.join()

            // should retain correlation
            assertCorrelation(correlationId, subCorrelationId)
        }

        // should retain correlation
        assertCorrelation(correlationId, null)
    }
}
