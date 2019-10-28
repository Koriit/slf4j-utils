package koriit.kotlin.slf4j.mdc.correlation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import org.slf4j.MDC
import kotlin.coroutines.CoroutineContext

val subCorrelationId: String? get() = MDC.get(MDC_SUB_CORRELATION_KEY)

fun withSubCorrelation(subCorrelationId: String = newCorrelationId()): CoroutineContext {
    val correlationId: String = correlationId ?: throw IllegalStateException("There is no correlation to branch from")

    return CorrelationId(correlationId) + SubCorrelationId(subCorrelationId)
}

suspend fun <T> withSubCorrelation(subCorrelationId: String = newCorrelationId(), block: suspend CoroutineScope.() -> T) = withContext(withSubCorrelation(subCorrelationId), block)
