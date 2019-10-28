package koriit.kotlin.slf4j.mdc.correlation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

fun continueCorrelation(context: CoroutineContext): CoroutineContext {
    val correlation = context[CorrelationId] ?: throw IllegalStateException("There is no correlation to continue")
    val subCorrelation = context[SubCorrelationId] ?: NoSubCorrelationId

    return correlation + subCorrelation
}

fun continueCorrelation(scope: CoroutineScope) = continueCorrelation(scope.coroutineContext)

suspend fun <T> continueCorrelation(context: CoroutineContext, block: suspend CoroutineScope.() -> T) = withContext(continueCorrelation(context), block)

suspend fun <T> continueCorrelation(scope: CoroutineScope, block: suspend CoroutineScope.() -> T) = withContext(continueCorrelation(scope.coroutineContext), block)

fun continueCorrelation(): CoroutineContext {
    val correlation = correlationId
            ?.let { CorrelationId(it) }
            ?: throw IllegalStateException("There is no correlation to continue")

    val subCorrelation = subCorrelationId
            ?.let { SubCorrelationId(it) }
            ?: NoSubCorrelationId

    return correlation + subCorrelation
}
