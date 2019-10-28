package koriit.kotlin.slf4j.mdc.correlation

import koriit.kotlin.slf4j.mdc.CoroutineMDCValue
import kotlin.coroutines.CoroutineContext

const val MDC_SUB_CORRELATION_KEY = "subCorrelationId"

internal class SubCorrelationId(
        value: String?
) : CoroutineMDCValue(MDC_SUB_CORRELATION_KEY, value, SubCorrelationId) {

    companion object Key : CoroutineContext.Key<SubCorrelationId>
}

internal val NoSubCorrelationId = SubCorrelationId(null)
