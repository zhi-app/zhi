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

import android.content.Context
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.ActivityScope
import com.zhi.common.app.AppContext
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
abstract class TwitterAuthProviderModule private constructor() {
    @Binds
    @ActivityScope
    @ActivityContext
    abstract fun twitterAuthProvider(authProvider: FirebaseTwitterAuthProvider): TwitterAuthProvider

    @Binds
    @IntoMap
    @ActivityContext
    @StringKey(TwitterAuthProvider.providerId)
    abstract fun authProvider(@ActivityContext authProvider: TwitterAuthProvider): AuthProvider

    @Module
    companion object {
        @JvmStatic
        @Provides
        @ActivityScope
        @ActivityContext
        fun twitterAuthClient(@AppContext context: Context): TwitterAuthClient {
            Twitter.initialize(
                TwitterConfig.Builder(context).twitterAuthConfig(
                    TwitterAuthConfig(
                        context.getString(com.zhi.service.auth.twitter.R.string.auth_twitter_client_id),
                        context.getString(com.zhi.service.auth.twitter.R.string.auth_twitter_client_secret)
                    )
                ).build()
            )
            return TwitterAuthClient()
        }
    }
}