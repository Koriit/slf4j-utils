@file:Suppress("NOTHING_TO_INLINE")

package com.koriit.kotlin.slf4j

import org.slf4j.Logger
import org.slf4j.Marker
import org.slf4j.MarkerFactory

/**
 * SLF4J marker for fatal logs.
 */
val FATAL: Marker = MarkerFactory.getMarker("FATAL")

/**
 * Is the logger instance enabled for the ERROR level with FATAL marker?
 *
 * @return True if this Logger is enabled for the ERROR with FATAL marker level, false otherwise.
 */
inline fun Logger.isFatalEnabled() = isErrorEnabled(FATAL)

/**
 * Log a message at the ERROR level with FATAL marker.
 *
 * @param msg the message string to be logged
 */
inline fun Logger.fatal(msg: String) = error(FATAL, msg)

/**
 * Log a message at the ERROR level with FATAL marker according to the specified format and argument.
 *
 * This form avoids superfluous object creation when the logger is disabled for the ERROR level with FATAL marker.
 *
 * @param format the format string
 * @param arg the argument
 */
inline fun Logger.fatal(format: String, arg: Any?) = error(FATAL, format, arg)

/**
 * Log a message at the ERROR level with FATAL marker according to the specified format and arguments.
 *
 * This form avoids superfluous object creation when the logger is disabled for the ERROR level with FATAL marker.
 *
 * @param format the format string
 * @param arg1 the first argument
 * @param arg2 the second argument
 */
inline fun Logger.fatal(format: String, arg1: Any?, arg2: Any?) = error(FATAL, format, arg1, arg2)

/**
 * Log a message at the ERROR level with FATAL marker according to the specified format and arguments.
 *
 * This form avoids superfluous string concatenation when the logger
 * is disabled for the ERROR level with FATAL marker. However, this variant incurs the hidden
 * (and relatively small) cost of creating an `Object[]` before invoking the method,
 * even if this logger is disabled for ERROR with FATAL marker. The variants taking
 * [one][Logger.fatal] and [two][Logger.fatal]
 * arguments exist solely in order to avoid this hidden cost.
 *
 * @param format the format string
 * @param arguments a list of 3 or more arguments
 */
inline fun Logger.fatal(format: String, vararg arguments: Any?) = error(FATAL, format, *arguments)

/**
 * Log an exception (throwable) at the ERROR level with FATAL marker and with an accompanying message.
 *
 * @param msg the message accompanying the exception
 * @param t the exception (throwable) to log
 */
inline fun Logger.fatal(msg: String, t: Throwable) = error(FATAL, msg, t)
