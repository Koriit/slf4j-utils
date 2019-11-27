package koriit.kotlin.slf4j

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.system.exitProcess
import kotlinx.coroutines.CoroutineExceptionHandler
import org.slf4j.Logger

/**
 * Create block of code that is watched for any thrown exceptions.
 *
 * This function is inlined to allow using private fields and functions inside the block.
 *
 * @param fatal Whether to log exception as fatal
 * @param ignore List of exceptions to ignore
 * @param block Your block of code
 */
@Suppress("TooGenericExceptionCaught") // It is intended to catch Throwable
inline fun Logger.watch(
    fatal: Boolean = false,
    ignore: List<KClass<*>> = emptyList(),
    block: () -> Unit
) {
    try {
        block()
    } catch (e: Throwable) {
        when {
            ignore.any { e::class.isSubclassOf(it) } -> return
            fatal -> fatal("Unexpected error: ${e.message}", e)
            else -> error("Unexpected error: ${e.message}", e)
        }
    }
}

/**
 * Creates coroutine exception handler which can be added to your coroutine context.
 *
 * All unhandled exceptions are logged.
 *
 * @param fatal Whether to log exception as fatal
 * @param shutdown Whether to shutdown JVM on unhandled exception, implies fatal
 */
fun Logger.watched(fatal: Boolean = false, shutdown: Boolean = false) = CoroutineExceptionHandler { _, e ->
    if (fatal || shutdown) {
        this.fatal("Unexpected problem: ${e.message}", e)
    } else {
        error("Unexpected problem: ${e.message}", e)
    }

    if (shutdown) {
        exitProcess(1)
    }
}
