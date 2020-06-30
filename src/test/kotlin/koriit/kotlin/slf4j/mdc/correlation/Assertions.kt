package koriit.kotlin.slf4j.mdc.correlation

import org.junit.jupiter.api.Assertions.assertEquals
import org.slf4j.MDC

fun assertCorrelation(correlationId: String?) {
    assertEquals(correlationId, MDC.get(MDC_CORRELATION_KEY))
}

fun assertSubCorrelation(correlationId: String?) {
    assertEquals(correlationId, MDC.get(MDC_SUB_CORRELATION_KEY))
}

fun assertCorrelation(correlationId: String?, subCorrelationId: String?) {
    assertCorrelation(correlationId)
    assertSubCorrelation(subCorrelationId)
}
