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

package com.zhi.common.glide

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.engine.cache.DiskCache
import com.bumptech.glide.load.engine.cache.MemoryCache
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.request.RequestOptions
import com.google.common.base.Optional
import com.zhi.common.net.NetContext
import dagger.BindsOptionalOf
import dagger.Module
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

class GlideConfigs {
    @Inject
    @NetContext
    lateinit var httpClientProvider: Provider<OkHttpClient>
    @Inject
    lateinit var bitmapPool: Optional<Provider<BitmapPool>>
    @Inject
    lateinit var memoryCache: Optional<Provider<MemoryCache>>
    @Inject
    lateinit var diskCacheFactory: Optional<Provider<DiskCache.Factory>>
    @Inject
    lateinit var requestOptions: Optional<Provider<RequestOptions>>
    @Inject
    lateinit var uncaughtThrowableStrategy: Optional<Provider<GlideExecutor.UncaughtThrowableStrategy>>
    @Inject
    @field:Named("glide-log-level")
    lateinit var logLevel: Optional<Int>
}

@Module
abstract class GlideConfigsModule private constructor() {
    @BindsOptionalOf
    internal abstract fun bitmapPool(): BitmapPool

    @BindsOptionalOf
    internal abstract fun memoryCache(): MemoryCache

    @BindsOptionalOf
    internal abstract fun diskCacheFactory(): DiskCache.Factory

    @BindsOptionalOf
    internal abstract fun requestOptions(): RequestOptions

    @BindsOptionalOf
    internal abstract fun uncaughtThrowableStrategy(): GlideExecutor.UncaughtThrowableStrategy

    @BindsOptionalOf
    @Named("glide-log-level")
    internal abstract fun logLevel(): Int
}