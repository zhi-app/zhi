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
import com.facebook.soloader.SoLoader
import com.facebook.sonar.android.AndroidSonarClient
import com.facebook.sonar.android.utils.SonarUtils
import com.facebook.sonar.core.SonarPlugin
import com.zhi.common.app.AppCreateCallback
import com.zhi.common.app.MainProcess
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import javax.inject.Inject
import javax.inject.Singleton

@Module
abstract class SonarModule private constructor() {
    @Multibinds
    internal abstract fun plugins(): Set<SonarPlugin>

    @Binds
    @IntoSet
    @Singleton
    @MainProcess
    internal abstract fun appCreateCallback(sonarService: SonarService): AppCreateCallback
}

@Singleton
internal class SonarService @Inject
constructor(private val plugins: @JvmSuppressWildcards(true) Set<SonarPlugin>) : AppCreateCallback {

    override fun onCreate(application: Application) {
        SoLoader.init(application, false)
        if (!SonarUtils.shouldEnableSonar(application)) {
            return
        }
        val client = AndroidSonarClient.getInstance(application)
        plugins.forEach { client.addPlugin(it) }
        client.start()
    }
}
