package koriit.kotlin.slf4j

import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val loggerSuffix = """\$.*$""".toRegex()

/**
 * Helper function to retrieve SLF4J Logger named after call site class.
 *
 * ```
 * private val LOG = logger {}
 * ```
 *
 * @param lambda Lambda object to determine class name of the call site, just pass empty `{}`
 */
fun logger(lambda: () -> Unit): Logger {
    return LoggerFactory.getLogger(lambda.javaClass.name.replace(loggerSuffix, ""))
}
