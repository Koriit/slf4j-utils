package koriit.kotlin.slf4j.mdc.correlation

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import org.slf4j.MDC

/**
 * Shorthand property to retrieve sub-correlation id from SLF4J MDC.
 */
val subCorrelationId: String? get() = MDC.get(MDC_SUB_CORRELATION_KEY)

/**
 * Creates new context element with current correlation id and new sub-correlation id.
 */
fun subCorrelated(subCorrelationId: String = newCorrelationId()): CoroutineContext {
    val correlationId: String = correlationId ?: throw IllegalStateException("There is no correlation to branch from")

    return CorrelationId(correlationId) + SubCorrelationId(subCorrelationId)
}

/**
 * Coroutine builder. Passed coroutine is executed with current correlation id and new sub-correlation id.
 */
suspend fun <T> withSubCorrelation(subCorrelationId: String = newCorrelationId(), block: suspend CoroutineScope.() -> T) = withContext(subCorrelated(subCorrelationId), block)
