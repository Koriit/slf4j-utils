package koriit.kotlin.slf4j.mdc.correlation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class CorrelationTest {

    @Test
    fun `should add correlation`() {
        val correlation1 = correlateThread()

        // assert thread correlation
        assertCorrelation(correlation1)

        val scope = CoroutineScope(correlated(correlation1))
        runBlocking {
            // should have the same correlation as the wrapping thread
            assertCorrelation(correlation1)

            // starting a coroutine with different correlation
            scope.launch(correlated()) {
                // should generate new correlation id
                val correlation2 = coroutineContext[CorrelationId]!!.value
                assertCorrelation(correlation2)
                assertNotEquals(correlation1, correlation2)

                withCorrelation {
                    // should generate new correlation id
                    val correlation3 = coroutineContext[CorrelationId]!!.value
                    assertCorrelation(correlation3)
                    assertNotEquals(correlation1, correlation3)
                    assertNotEquals(correlation2, correlation3)
                }
                // should retain correlation
                assertCorrelation(correlation2)
            }.join()

            // should retain correlation
            assertCorrelation(correlation1)
        }

        // should retain correlation
        assertCorrelation(correlation1)
        clearThreadCorrelation()
    }

    @Test
    fun `should add correlation and sub correlation`() {
        correlateThread() // make sure there is some correlation already
        val correlation = newCorrelationId()
        val subCorrelation = newCorrelationId()
        runBlocking(correlated(correlation, subCorrelation)) {
            assertCorrelation(correlation, subCorrelation)
        }
        clearThreadCorrelation()
    }

    @Test
    fun `should generate correlation and sub correlation`() {
        clearThreadCorrelation() // ensure no correlation
        runBlocking(correlated() + subCorrelated()) {
            assertFalse(correlationId.isNullOrBlank())
            assertFalse(subCorrelationId.isNullOrBlank())
            assertNotEquals(correlationId, subCorrelationId)
        }
        clearThreadCorrelation()
    }
}
