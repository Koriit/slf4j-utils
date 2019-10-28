package koriit.kotlin.slf4j.mdc.correlation

import koriit.kotlin.slf4j.mdc.CoroutineMDCValue
import kotlin.coroutines.CoroutineContext

const val MDC_CORRELATION_KEY = "correlationId"

internal class CorrelationId(
        override val value: String
) : CoroutineMDCValue(MDC_CORRELATION_KEY, value, CorrelationId) {

    companion object Key : CoroutineContext.Key<CorrelationId>
}
