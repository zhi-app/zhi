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

package com.zhi.service.auth

import com.zhi.common.app.ActivityContext
import com.zhi.common.app.ActivityScope
import com.zhi.common.net.NetContext
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import retrofit2.Retrofit

@Module
abstract class GitHubAuthBaseModule private constructor() {

    @Binds
    @IntoMap
    @ActivityContext
    @StringKey(GitHubAuthProvider.providerId)
    abstract fun authProvider(@ActivityContext gitHubAuthProvider: GitHubAuthProvider): AuthProvider

    @Module
    companion object {
        @JvmStatic
        @Provides
        @ActivityScope
        @ActivityContext
        fun gitHubAuthTokenService(@NetContext retrofit: Retrofit): GitHubAuthTokenService {
            return retrofit.create(GitHubAuthTokenService::class.java)
        }

        @JvmStatic
        @Provides
        @ActivityScope
        @ActivityContext
        fun gitHubAuthApiService(@NetContext retrofit: Retrofit): GitHubAuthApiService {
            return retrofit.create(GitHubAuthApiService::class.java)
        }
    }
}