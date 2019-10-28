package koriit.kotlin.slf4j.mdc

import kotlinx.coroutines.ThreadContextElement
import org.slf4j.MDC
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

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