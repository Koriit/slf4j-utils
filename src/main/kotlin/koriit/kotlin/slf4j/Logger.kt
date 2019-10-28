package koriit.kotlin.slf4j

import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val loggerSuffix = """\$.*$""".toRegex()

fun logger(lambda: () -> Unit): Logger {
    return LoggerFactory.getLogger(lambda.javaClass.name.replace(loggerSuffix, ""))
}
