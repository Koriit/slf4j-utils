package koriit.kotlin.slf4j.mdc.correlation

import org.slf4j.MDC

/**
 * Puts correlation id into thread's MDC.
 */
fun correlateThread(correlationId: String = newCorrelationId()): String {
    MDC.put(MDC_CORRELATION_KEY, correlationId)

    return correlationId
}

/**
 * Puts correlation id and sub-correlation into thread's MDC.
 */
fun subCorrelateThread(correlationId: String, subCorrelationId: String = newCorrelationId()): String {
    MDC.put(MDC_CORRELATION_KEY, correlationId)
    MDC.put(MDC_SUB_CORRELATION_KEY, subCorrelationId)

    return subCorrelationId
}

/**
 * Puts sub-correlation into thread's MDC.
 */
fun subCorrelateThread(subCorrelationId: String = newCorrelationId()): String =
    subCorrelateThread(correlationId ?: throw IllegalStateException("There is no correlation to branch from"), subCorrelationId)

/**
 * Clears any correlation from thread's MDC.
 */
fun clearThreadCorrelation() {
    MDC.remove(MDC_CORRELATION_KEY)
    MDC.remove(MDC_SUB_CORRELATION_KEY)
}
