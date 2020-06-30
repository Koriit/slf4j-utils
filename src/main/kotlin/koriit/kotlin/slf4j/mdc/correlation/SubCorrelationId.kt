package koriit.kotlin.slf4j.mdc.correlation

import koriit.kotlin.slf4j.mdc.CoroutineMDCValue
import kotlin.coroutines.CoroutineContext

/**
 * MDC Key for correlation value.
 */
const val MDC_SUB_CORRELATION_KEY = "subCorrelationId"

/**
 * Coroutine Context element holding correlation id.
 */
class SubCorrelationId(
    value: String?
) : CoroutineMDCValue(MDC_SUB_CORRELATION_KEY, value, SubCorrelationId) {

    /**
     * A key of this coroutine context element.
     */
    companion object Key : CoroutineContext.Key<SubCorrelationId>
}

/**
 * Empty sub-correlation. Clears sub-correlation if added to context.
 */
val NoSubCorrelationId = SubCorrelationId(null)
