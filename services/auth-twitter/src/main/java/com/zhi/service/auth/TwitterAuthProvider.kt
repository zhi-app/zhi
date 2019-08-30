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

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.twitter.sdk.android.core.models.User
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.BaseActivity
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.startForResult
import com.zhi.common.util.Logs
import dagger.Lazy

abstract class TwitterAuthProvider
constructor(
    @ActivityContext private val client: Lazy<TwitterAuthClient>
) : AuthProvider {

    override fun signIn(host: BaseFragment) {
        super.signIn(host)

        (host.activity as BaseActivity).startForResult(RC_SIGN_IN) { resultCode, data ->
            client.get().onActivityResult(RC_SIGN_IN, resultCode, data)
        }

        client.get().authorize(host.activity, object : Callback<TwitterSession>() {
            override fun success(session: Result<TwitterSession>) {
                Logs.d(TAG, "Twitter auth success")
                TwitterCore.getInstance().apiClient.accountService.verifyCredentials(
                    false,
                    false,
                    true
                ).enqueue(object : Callback<User>() {
                    override fun success(result: Result<User>) {
                        Logs.d(TAG, "Twitter auth verify success")
                        signInWith(
                            session.data.authToken.token,
                            session.data.authToken.secret
                        )
                    }

                    override fun failure(exception: TwitterException) {
                        Logs.d(TAG, "Twitter auth verify failed $exception")
                    }
                })
            }

            override fun failure(exception: TwitterException) {
                Logs.d(TAG, "Twitter auth failed $exception")
            }
        })
    }

    protected abstract fun signInWith(token: String, secret: String)

    override val id: String
        get() = providerId

    companion object {
        const val RC_SIGN_IN = TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE
        const val providerId = "twitter.com"
        const val TAG = "Twitter"
    }
}