package com.koriit.kotlin.slf4j.mdc.correlation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class SubCorrelationTest {

    @Test
    fun `should add sub correlation`() {
        correlateThread()
        val subCorrelation1 = subCorrelateThread()
        assertSubCorrelation(subCorrelation1)

        val scope = CoroutineScope(continueCorrelation())
        runBlocking {
            // should have the same correlation as the wrapping thread
            assertSubCorrelation(subCorrelation1)

            // should generate new sub-correlation id
            scope.launch(subCorrelated()) {
                val subCorrelation2 = coroutineContext[SubCorrelationId]!!.value
                assertSubCorrelation(subCorrelation2)
                assertNotEquals(subCorrelation1, subCorrelation2)

                // should generate new sub-correlation id
                withSubCorrelation {
                    val subCorrelation3 = coroutineContext[SubCorrelationId]!!.value
                    assertSubCorrelation(subCorrelation3)
                    assertNotEquals(subCorrelation1, subCorrelation3)
                    assertNotEquals(subCorrelation2, subCorrelation3)
                }

                // should retain correlation
                assertSubCorrelation(subCorrelation2)
            }.join()

            // should retain correlation
            assertSubCorrelation(subCorrelation1)
        }

        // should retain correlation
        assertSubCorrelation(subCorrelation1)
        clearThreadCorrelation()
    }
}
