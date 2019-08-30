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

import dagger.Lazy

import dagger.Subcomponent
import dagger.android.AndroidInjector
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Scope

class NetApp {
    @Inject
    @field:NetAppContext
    lateinit var httpClient: Lazy<OkHttpClient>

    @Inject
    @field:NetAppContext
    lateinit var retrofit: Lazy<Retrofit>
}

@Subcomponent(
    modules = [
        NetAppHttpClientModule::class,
        NetAppRetrofitModule::class
    ]
)
@NetScope
interface NetAppComponent : AndroidInjector<NetApp> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<NetApp>()
}

@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class NetScope

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class NetContext

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class NetAppContext