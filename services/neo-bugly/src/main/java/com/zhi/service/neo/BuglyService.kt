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

package com.zhi.service.neo

import android.app.Application
import com.tencent.bugly.crashreport.CrashReport
import com.zhi.common.app.AppCreateCallback
import com.zhi.common.app.MainProcess
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import java.io.BufferedReader
import java.io.FileReader
import javax.inject.Singleton

@Module
object BuglyModule {
    @JvmStatic
    @Provides
    @IntoSet
    @Singleton
    @MainProcess
    fun appCreateBuglyCallback() = object : AppCreateCallback {
        override fun onCreate(application: Application) {
            val appContext = application.applicationContext
            CrashReport.UserStrategy(appContext).also { strategy ->
                val res = appContext.resources
                val packageName = appContext.packageName
                val processName = getProcessName(android.os.Process.myPid())
                strategy.isUploadProcess = processName == null || processName == packageName
                strategy.appReportDelay =
                        res.getInteger(R.integer.neo_bugly_app_report_delays_ms).toLong()
                CrashReport.initCrashReport(application, strategy)
                CrashReport.setIsDevelopmentDevice(
                    appContext, res.getBoolean(R.bool.neo_bugly_enable_debug)
                )
            }
        }

        private fun getProcessName(pid: Int): String? =
            BufferedReader(FileReader("/proc/$pid/cmdline")).use { reader ->
                reader.readLine().takeIf { it.isNotEmpty() }?.trim { it <= ' ' }
            }
    }

}