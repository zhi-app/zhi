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

package com.zhi.common.net

import com.google.common.base.Optional
import dagger.BindsOptionalOf
import dagger.Module
import dagger.multibindings.Multibinds
import okhttp3.Call
import okhttp3.Dns
import okhttp3.Interceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import java.util.concurrent.Executor
import javax.inject.Inject

class NetConfigs {
    @Inject
    lateinit var dns: Optional<Dns>
    @Inject
    lateinit var interceptors: @JvmSuppressWildcards(true) Set<Interceptor>
    @Inject
    @field:NetContext
    lateinit var netInterceptors: @JvmSuppressWildcards(true) Set<Interceptor>
}

@Module
abstract class NetConfigsModule private constructor() {

    @Multibinds
    internal abstract fun interceptors(): Set<Interceptor>

    @Multibinds
    @NetContext
    internal abstract fun networkInterceptors(): Set<Interceptor>

    @Multibinds
    internal abstract fun converters(): Set<Converter.Factory>

    @Multibinds
    internal abstract fun adapters(): Set<CallAdapter.Factory>

    @BindsOptionalOf
    internal abstract fun dns(): Dns

    @BindsOptionalOf
    internal abstract fun callFactor(): Call.Factory

    @BindsOptionalOf
    internal abstract fun callbackExecutor(): Executor
}