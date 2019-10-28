package koriit.kotlin.slf4j.mdc.correlation

import org.slf4j.MDC

fun correlateThread(correlationId: String = newCorrelationId()): String {
    MDC.put(MDC_CORRELATION_KEY, correlationId)

    return correlationId
}

fun subCorrelateThread(correlationId: String, subCorrelationId: String = newCorrelationId()): String {
    MDC.put(MDC_CORRELATION_KEY, correlationId)
    MDC.put(MDC_SUB_CORRELATION_KEY, subCorrelationId)

    return subCorrelationId
}

fun clearThreadCorrelation() {
    MDC.remove(MDC_CORRELATION_KEY)
    MDC.remove(MDC_SUB_CORRELATION_KEY)
}
