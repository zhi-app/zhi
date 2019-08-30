/*
 * Copyright [2018] [zhi]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhi.common.util

import android.app.Application
import android.util.Log
import com.zhi.common.R
import com.zhi.common.app.AppCreateCallback
import com.zhi.common.app.MainProcess
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
object LogsModule {
    @JvmStatic
    @Provides
    @IntoSet
    @MainProcess
    fun appCreateLogsCallback() = object : AppCreateCallback {
        override fun onCreate(application: Application) {
            application.resources.also { res ->
                Logs.maxEnabledLogLevel = res.getInteger(R.integer.common_max_enabled_log_level)
                Logs.logTag = res.getString(R.string.common_log_prefix)
            }
        }
    }
}

object Logs {
    private const val MAX_LOG_TAG_LENGTH = 23

    internal var logTag = "zhi"
    internal var maxEnabledLogLevel = Log.VERBOSE

    /**
     * Send a [Log.VERBOSE] log message.
     *
     * @see [Log.v]
     */
    fun v(any: Any, format: String) = with(any, Log.VERBOSE)?.let {
        Log.v(it, format)
    }

    /**
     * Send a [Log.VERBOSE] log message.
     *
     * @see [Log.v]
     */
    fun v(any: Any, format: String, tr: Throwable) = with(any, Log.VERBOSE)?.let {
        Log.v(it, format, tr)
    }

    /**
     * Send a [Log.DEBUG] log message.
     *
     * @see [Log.d]
     */
    fun d(any: Any, format: String) = with(any, Log.DEBUG)?.let {
        Log.d(it, format)
    }

    /**
     * Send a [Log.DEBUG] log message.
     *
     * @see [Log.d]
     */
    fun d(any: Any, format: String, tr: Throwable) = with(any, Log.DEBUG)?.let {
        Log.d(it, format, tr)
    }

    /**
     * Send a [Log.INFO] log message.
     *
     * @see [Log.i]
     */
    fun i(any: Any, format: String) = with(any, Log.INFO)?.let {
        Log.i(it, format)
    }

    /**
     * Send a [Log.INFO] log message.
     *
     * @see [Log.i]
     */
    fun i(any: Any, format: String, tr: Throwable) = with(any, Log.INFO)?.let {
        Log.i(it, format, tr)
    }

    /**
     * Send a [Log.WARN] log message.
     *
     * @see [Log.w]
     */
    fun w(any: Any, format: String) = with(any, Log.WARN)?.let {
        Log.w(it, format)
    }

    /**
     * Send a [Log.WARN] log message.
     *
     * @see [Log.w]
     */
    fun w(any: Any, format: String, tr: Throwable) = with(any, Log.WARN)?.let {
        Log.w(it, format, tr)
    }

    /**
     * Send a [Log.ERROR] log message.
     *
     * @see [Log.e]
     */
    fun e(any: Any, format: String) = with(any, Log.ERROR)?.let {
        Log.e(it, format)
    }

    /**
     * Send a [Log.ERROR] log message.
     *
     * @see [Log.e]
     */
    fun e(any: Any, format: String, tr: Throwable) = with(any, Log.ERROR)?.let {
        Log.e(it, format, tr)
    }

    /**
     * What a Terrible Failure
     *
     * @see [Log.wtf]
     */
    fun wtf(any: Any, format: String) = with(any).let {
        Log.wtf(it, format)
    }

    /**
     * What a Terrible Failure
     *
     * @see [Log.wtf]
     */
    fun wtf(any: Any, format: String, tr: Throwable) = with(any).let {
        Log.wtf(it, format, tr)
    }

    /**
     * With any object as log tag and insert common prefix [Logs.logTag].
     */
    fun with(any: Any) = when (any) {
        is String -> any
        else -> logTag.plus(any.javaClass.simpleName).run {
            if (length > MAX_LOG_TAG_LENGTH) {
                substring(0, MAX_LOG_TAG_LENGTH - 1)
            } else {
                this
            }
        }
    }

    /**
     * Checks to see whether or not a log for the specified tag is loggable at the specified level.
     */
    private fun with(any: Any, level: Int): String? = when {
        maxEnabledLogLevel <= level -> null

        any is String -> when {
            Log.isLoggable(any, level) -> any
            any.startsWith(logTag) && Log.isLoggable(logTag, level) -> any
            else -> null
        }
        else -> when {
            !Log.isLoggable(logTag, level) -> null
            else -> with(any)
        }
    }
}
