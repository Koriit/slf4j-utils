package koriit.kotlin.slf4j.mdc.correlation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import org.slf4j.MDC
import java.util.*
import kotlin.coroutines.CoroutineContext

val correlationId: String? get() = MDC.get(MDC_CORRELATION_KEY)

fun newCorrelationId(): String = UUID.randomUUID().toString()

fun correlated(correlationId: String = newCorrelationId()): CoroutineContext = CorrelationId(correlationId) + NoSubCorrelationId

suspend fun <T> withCorrelation(correlationId: String = newCorrelationId(), block: suspend CoroutineScope.() -> T) = withContext(correlated(correlationId), block)
