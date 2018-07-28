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

import android.content.Context
import com.facebook.litho.sonar.LithoSonarDescriptors
import com.facebook.sonar.core.SonarPlugin
import com.facebook.sonar.plugins.inspector.DescriptorMapping
import com.facebook.sonar.plugins.inspector.InspectorSonarPlugin
import com.facebook.sonar.plugins.network.NetworkSonarPlugin
import com.facebook.sonar.plugins.network.SonarOkhttpInterceptor
import com.facebook.sonar.plugins.sandbox.SandboxSonarPlugin
import com.facebook.sonar.plugins.sandbox.SandboxSonarPluginStrategy
import com.facebook.sonar.plugins.sharedpreferences.SharedPreferencesSonarPlugin
import com.google.common.base.Optional
import com.zhi.common.app.AppContext
import com.zhi.common.net.Network
import dagger.Binds
import dagger.BindsOptionalOf
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class SonarNetworkPluginModule private constructor() {
    @Binds
    @IntoSet
    @Singleton
    internal abstract fun plugin(plugin: NetworkSonarPlugin): SonarPlugin

    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        internal fun sonarPlugin(): NetworkSonarPlugin = NetworkSonarPlugin()

        @JvmStatic
        @Provides
        @IntoSet
        @Singleton
        @Network
        internal fun interceptor(plugin: NetworkSonarPlugin): Interceptor =
            SonarOkhttpInterceptor(plugin)
    }
}

@Module
abstract class SonarInspectorPluginModule private constructor() {
    @Binds
    @IntoSet
    @Singleton
    internal abstract fun plugin(plugin: InspectorSonarPlugin): SonarPlugin

    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        internal fun sonarPlugin(@AppContext context: Context): InspectorSonarPlugin {
            val descriptorMapping = DescriptorMapping.withDefaults()
            LithoSonarDescriptors.add(descriptorMapping)
            return InspectorSonarPlugin(context, descriptorMapping)
        }
    }
}

@Module
abstract class SonarSharedPreferencePluginModule private constructor() {
    @BindsOptionalOf
    @SonarSharedPreference
    internal abstract fun sharedPreferenceName(): String

    @Binds
    @IntoSet
    @Singleton
    internal abstract fun plugin(plugin: SharedPreferencesSonarPlugin): SonarPlugin

    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        internal fun sonarPlugin(
            @AppContext context: Context,
            @SonarSharedPreference name: Optional<String>
        ): SharedPreferencesSonarPlugin =
            SharedPreferencesSonarPlugin(context, if (name.isPresent) name.get() else null)

        @Named("sonar-shared-preference")
        annotation class SonarSharedPreference
    }
}

@Module
abstract class SonarSandboxPluginModule private constructor() {
    @Binds
    @IntoSet
    @Singleton
    internal abstract fun plugin(plugins: SandboxSonarPlugin): SonarPlugin

    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        internal fun sonarPlugin(strategy: SandboxSonarPluginStrategy): SonarPlugin =
            SandboxSonarPlugin(strategy)
    }
}