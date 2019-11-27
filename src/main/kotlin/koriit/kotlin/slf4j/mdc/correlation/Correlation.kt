package koriit.kotlin.slf4j.mdc.correlation

import java.util.UUID
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import org.slf4j.MDC

/**
 * Shorthand property to retrieve correlation id from SLF4J MDC.
 */
val correlationId: String? get() = MDC.get(MDC_CORRELATION_KEY)

/**
 * Shorthand function to create new correlation id.
 *
 * UUID is only a reccommendation. Correlation id can be any quasi-unique string.
 */
fun newCorrelationId(): String = UUID.randomUUID().toString()

/**
 * Creates new context element with new correlation id and no sub-correlation id.
 */
fun correlated(correlationId: String = newCorrelationId()): CoroutineContext = CorrelationId(correlationId) + NoSubCorrelationId

/**
 * Coroutine builder. Passed coroutine is executed with new correlation id and no sub-correlation id.
 */
suspend fun <T> withCorrelation(correlationId: String = newCorrelationId(), block: suspend CoroutineScope.() -> T) = withContext(correlated(correlationId), block)
