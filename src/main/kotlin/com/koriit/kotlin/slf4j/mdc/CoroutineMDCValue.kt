package com.koriit.kotlin.slf4j.mdc

import kotlinx.coroutines.ThreadContextElement
import org.slf4j.MDC
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * Abstract coroutine context element that will make sure that given name-value pair
 * is present in the SLF4J MDC when coroutine is running.
 *
 * Concrete implementations should provide static name.
 *
 * @property name MDC key
 * @property value MDC value
 * @property contextKey The reference that is going to be a context key for this value
 */
abstract class CoroutineMDCValue(
    val name: String,
    open val value: String?,
    val contextKey: CoroutineContext.Key<*>
) : ThreadContextElement<String?>, AbstractCoroutineContextElement(contextKey) {

    override fun updateThreadContext(context: CoroutineContext): String? {
        val oldState = MDC.get(name)
        setCurrent(value)
        return oldState
    }

    override fun restoreThreadContext(context: CoroutineContext, oldState: String?) {
        setCurrent(oldState)
    }

    private fun setCurrent(value: String?) {
        if (value == null) {
            MDC.remove(name)
        } else {
            MDC.put(name, value)
        }
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(value=$value, name=$name, contextKey=$contextKey)"
    }
}
