package koriit.kotlin.slf4j.mdc.correlation

import koriit.kotlin.slf4j.mdc.CoroutineMDCValue
import kotlin.coroutines.CoroutineContext

/**
 * MDC Key for correlation value.
 */
const val MDC_CORRELATION_KEY = "correlationId"

/**
 * Coroutine Context element holding correlation id.
 *
 * @property value The correlation id that is going to be stored with this context value
 */
class CorrelationId(
    override val value: String
) : CoroutineMDCValue(MDC_CORRELATION_KEY, value, CorrelationId) {

    /**
     * A key of this coroutine context element.
     */
    companion object Key : CoroutineContext.Key<CorrelationId>
}
