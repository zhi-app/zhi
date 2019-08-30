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

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.executor.GlideExecutor.newDiskCacheExecutor
import com.bumptech.glide.load.engine.executor.GlideExecutor.newSourceExecutor
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

@GlideModule(glideName = "Glide")
internal class GlideAppModule : AppGlideModule() {
    // disable manifest parsing to avoid adding similar modules twice
    override fun isManifestParsingEnabled(): Boolean = false

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(
            GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(
                configs.httpClientProvider.get()
            )
        )
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        if (configs.bitmapPool.isPresent) {
            builder.setBitmapPool(configs.bitmapPool.get().get())
        }
        if (configs.memoryCache.isPresent) {
            builder.setMemoryCache(configs.memoryCache.get().get())
        }
        if (configs.diskCacheFactory.isPresent) {
            builder.setDiskCache(configs.diskCacheFactory.get().get())
        }
        if (configs.requestOptions.isPresent) {
            builder.setDefaultRequestOptions(configs.requestOptions.get().get())
        }
        if (configs.uncaughtThrowableStrategy.isPresent) {
            val strategy = configs.uncaughtThrowableStrategy.get().get()
            builder.setDiskCacheExecutor(newDiskCacheExecutor(strategy))
            builder.setSourceExecutor(newSourceExecutor(strategy))
        }
        if (configs.logLevel.isPresent) {
            builder.setLogLevel(configs.logLevel.get())
        }
    }

    companion object {
        val configs = GlideConfigs()
    }
}