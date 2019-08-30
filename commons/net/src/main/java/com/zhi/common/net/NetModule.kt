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

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Provider
import javax.inject.Singleton

@Module(subcomponents = [NetAppComponent::class])
object NetModule {
    @JvmStatic
    @Provides
    @Singleton
    @NetContext
    fun netApp(netAppComponentBuilderProvider: Provider<NetAppComponent.Builder>) = NetApp().apply {
        netAppComponentBuilderProvider.get().create(this).inject(this)
    }

    @JvmStatic
    @Provides
    @Singleton
    @NetContext
    fun httpClient(@NetContext netApp: NetApp): OkHttpClient = netApp.httpClient.get()

    @JvmStatic
    @Provides
    @Singleton
    @NetContext
    fun retrofit(@NetContext netApp: NetApp): Retrofit = netApp.retrofit.get()
}