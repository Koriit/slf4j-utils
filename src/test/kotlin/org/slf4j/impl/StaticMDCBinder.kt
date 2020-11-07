package org.slf4j.impl

import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.spi.MDCAdapter

/**
 * It overrides [org.slf4j.impl.StaticMDCBinder] (which binds [NOPMDCAdapter]) to bind [BasicMDCAdapter].
 */
class StaticMDCBinder private constructor() {

    companion object {
        @JvmStatic
        val SINGLETON = StaticMDCBinder()

        @JvmStatic
        fun getSingleton(): StaticMDCBinder {
            return SINGLETON
        }
    }

    fun getMDCA(): MDCAdapter {
        return BasicMDCAdapter()
    }

    fun getMDCAdapterClassStr(): String {
        return BasicMDCAdapter::class.java.name
    }
}
