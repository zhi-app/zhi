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

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.HostResultCallbacks
import com.zhi.common.util.BaseRuntimeException
import com.zhi.common.util.Logs
import dagger.Lazy

abstract class GoogleAuthProvider
constructor(
    private val client: Lazy<GoogleSignInClient>,
    private val authResource: Lazy<AuthResource>
) : AuthProvider {
    override fun signIn(host: BaseFragment) {
        super.signIn(host)
        client.get().silentSignIn().run {
            if (isSuccessful) {
                handleSignInResult(host, this, true)
            } else {
                addOnCompleteListener {

                    handleSignInResult(host, it, true)
                }
            }
        }
    }

    private fun handleSignInResult(
        host: BaseFragment,
        task: Task<GoogleSignInAccount>,
        silentSignIn: Boolean
    ) {
        try {
            val account = task.getResult(ApiException::class.java)!!
            signInWith(account.idToken!!)
            val authUser = GoogleAuthUser(account)
            authResource.get().success(authUser)
            return
        } catch (e: ApiException) {
            e.printStackTrace()
            Logs.e(
                this, "Status:Failed-${if (silentSignIn) "SilentSignIn" else "NormalSignIn"}," +
                        " errorCode := ${GoogleSignInStatusCodes.getStatusCodeString(e.statusCode)}," +
                        " error := $e"
            )
            if (!silentSignIn) {
                authResource.get().failure(e)
                return
            }
        }
        authResource.get().loading()
        HostResultCallbacks.startActivityForResult(
            host,
            RC_SIGN_IN,
            intent = client.get().signInIntent
        ) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                handleSignInResult(host, GoogleSignIn.getSignedInAccountFromIntent(data), false)
            } else {
                authResource.get().failure(BaseRuntimeException("Auth Failed"))
            }
        }
    }

    protected abstract fun signInWith(token: String)

    override val id: String
        get() = providerId

    companion object {
        private const val RC_SIGN_IN = 0xff01
        const val providerId: String = "google.com"
    }
}