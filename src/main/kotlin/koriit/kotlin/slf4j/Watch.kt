package koriit.kotlin.slf4j

import kotlinx.coroutines.CoroutineExceptionHandler
import org.slf4j.Logger
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.system.exitProcess

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