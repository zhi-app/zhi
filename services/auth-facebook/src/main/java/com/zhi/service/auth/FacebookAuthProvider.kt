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

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.internal.CallbackManagerImpl
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.HostResultCallbacks.Companion.startForResult
import com.zhi.common.util.BaseRuntimeException
import com.zhi.common.util.Logs
import dagger.Lazy

abstract class FacebookAuthProvider
constructor(
    private val authResource: Lazy<AuthResource>
) : AuthProvider {

    private lateinit var callbackManager: CallbackManager

    override fun signIn(host: BaseFragment) {
        super.signIn(host)

        if (!::callbackManager.isInitialized) {
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult) {
                        getAuthUser(result.accessToken)
                    }

                    override fun onCancel() {
                        authResource.get().failure(BaseRuntimeException("Auth canceled"))
                    }

                    override fun onError(error: FacebookException) {
                        authResource.get().failure(error)
                    }
                })
        }

        LoginManager.getInstance().logInWithReadPermissions(host, listOf("email", "public_profile"))
        startForResult(host, RC_SIGN_IN) { resultCode, data ->
            callbackManager.onActivityResult(RC_SIGN_IN, resultCode, data)
        }
    }

    private fun getAuthUser(accessToken: AccessToken) {
        GraphRequest.newMeRequest(accessToken) { obj, res ->
            res.error?.run {
                Logs.d(this@FacebookAuthProvider, "Failed to get user info")
                return@newMeRequest
            }
            if (obj == null) {
                Logs.d(this@FacebookAuthProvider, "Facebook graph request failed")
                return@newMeRequest
            }
            val authUser = FacebookAuthUser(
                email = obj.optString("email"),
                name = obj.optString("name"),
                avatar = obj.optJSONObject("picture")
                    ?.optJSONObject("data")
                    ?.optString("url") ?: ""
            )
            Logs.d(this@FacebookAuthProvider, "Facebook user is $authUser")
            signInWith(accessToken.token)

        }.run {
            parameters = Bundle().also { params ->
                params.putString("fields", "id,name,email,picture")
            }
            executeAsync()
        }
    }

    protected abstract fun signInWith(token: String)

    override val id: String
        get() = providerId

    companion object {
        private val RC_SIGN_IN = CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()
        const val providerId = "facebook.com"
    }
}