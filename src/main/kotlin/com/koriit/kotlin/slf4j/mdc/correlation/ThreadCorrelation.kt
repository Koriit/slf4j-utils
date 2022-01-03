package com.koriit.kotlin.slf4j.mdc.correlation

import org.slf4j.MDC

/**
 * Puts correlation id into thread's MDC.
 */
fun correlateThread(correlationId: String = newCorrelationId()): String {
    MDC.put(MDC_CORRELATION_KEY, correlationId)
    MDC.remove(MDC_SUB_CORRELATION_KEY)

    return correlationId
}

/**
 * Puts correlation id and sub-correlation into thread's MDC.
 */
fun correlateThread(correlationId: String, subCorrelationId: String) {
    MDC.put(MDC_CORRELATION_KEY, correlationId)
    MDC.put(MDC_SUB_CORRELATION_KEY, subCorrelationId)
}

/**
 * Puts sub-correlation into thread's MDC.
 */
fun subCorrelateThread(subCorrelationId: String = newCorrelationId()): String {
    MDC.put(MDC_CORRELATION_KEY, correlationId ?: throw IllegalStateException("There is no correlation to branch from"))
    MDC.put(MDC_SUB_CORRELATION_KEY, subCorrelationId)

    return subCorrelationId
}

/**
 * Clears any correlation from thread's MDC.
 */
fun clearThreadCorrelation() {
    MDC.remove(MDC_CORRELATION_KEY)
    MDC.remove(MDC_SUB_CORRELATION_KEY)
}

/**
 * Clears any sub-correlation from thread's MDC.
 */
fun clearThreadSubCorrelation() {
    MDC.remove(MDC_SUB_CORRELATION_KEY)
}
