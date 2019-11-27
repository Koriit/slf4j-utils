package koriit.kotlin.slf4j.mdc.correlation

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

/**
 * Creates new context element with current correlation and optional sub-correlation.
 */
fun continueCorrelation(): CoroutineContext {
    val correlation = correlationId
        ?.let { CorrelationId(it) }
        ?: throw IllegalStateException("There is no correlation to continue")

    val subCorrelation = subCorrelationId
        ?.let { SubCorrelationId(it) }
        ?: NoSubCorrelationId

    return correlation + subCorrelation
}

/**
 * Creates new context element with correlation and optional sub-correlation copied from passed coroutine context.
 */
fun continueCorrelation(context: CoroutineContext): CoroutineContext {
    val correlation = context[CorrelationId] ?: throw IllegalStateException("There is no correlation to continue")
    val subCorrelation = context[SubCorrelationId] ?: NoSubCorrelationId

    return correlation + subCorrelation
}

/**
 * Creates new context element with correlation and optional sub-correlation copied from passed coroutine scope.
 */
fun continueCorrelation(scope: CoroutineScope) = continueCorrelation(scope.coroutineContext)

/**
 * Coroutine builder. Passed coroutine is executed with correlation and optional sub-correlation of passed coroutine context.
 */
suspend fun <T> continueCorrelation(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T = withContext(continueCorrelation(context), block)

/**
 * Coroutine builder. Passed coroutine is executed with correlation and optional sub-correlation of passed coroutine scope.
 */
suspend fun <T> continueCorrelation(scope: CoroutineScope, block: suspend CoroutineScope.() -> T): T = withContext(continueCorrelation(scope.coroutineContext), block)
