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
import android.net.Uri
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.HostResultCallbacks.Companion.startActivityForResult
import com.zhi.common.rx.Schedulers
import com.zhi.common.util.BaseRuntimeException
import com.zhi.common.util.Logs
import com.zhi.service.auth.GitHubAuthActivity.Companion.EXTRA_URL
import com.zhi.service.auth.github.R
import dagger.Lazy
import io.reactivex.disposables.Disposable

abstract class GitHubAuthProvider
constructor(
    private val authResource: Lazy<AuthResource>,
    private val authTokenService: Lazy<GitHubAuthTokenService>,
    private val authApiService: Lazy<GitHubAuthApiService>
) : AuthProvider {

    private var disposable: Disposable? = null

    override fun signIn(host: BaseFragment) {
        super.signIn(host)

        val intent = GitHubAuthActivity.newIntent(host.context!!)
            .putExtra(
                EXTRA_URL,
                Uri.Builder().scheme("https").authority("github.com")
                    .path("login/oauth/authorize")
                    .appendQueryParameter(
                        "client_id",
                        host.getString(R.string.auth_github_client_id)
                    )
                    .appendQueryParameter("scope", "user:email")
                    .build()
            )

        startActivityForResult(host, RC_SIGN_IN, intent) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                authResource.get().success("Success")
                val code = data?.getStringExtra(GitHubAuthActivity.EXTRA_URL)?.let { url ->
                    Uri.parse(url).getQueryParameter("code")
                }
                getAuthToken(host, code!!)
            } else {
                authResource.get().failure(BaseRuntimeException("Auth Failed"))
            }
        }
    }

    private fun getAuthToken(host: BaseFragment, code: String) {
        disposable?.dispose()
        disposable = authTokenService.get().getAuthToken(
            clientId = host.getString(R.string.auth_github_client_id),
            clientSecret = host.getString(R.string.auth_github_client_secret),
            code = code
        ).observeOn(Schedulers.mainThread())
            .doOnTerminate { disposable = null }
            .subscribe({ token ->
                run {
                    Logs.d(this@GitHubAuthProvider, "github token is $token")
                    getAuthUser(token.accessToken)
                }
            }, { e ->
                Logs.d(this@GitHubAuthProvider, "github token error $e")
            })
    }

    private fun getAuthUser(token: String) {
        disposable?.dispose()
        disposable = authApiService.get().getAuthUser(
            token = "token $token"
        ).observeOn(Schedulers.mainThread())
            .doOnTerminate { disposable = null }
            .subscribe({ githubUser ->
                Logs.d(this@GitHubAuthProvider, "github user is $githubUser")
                signInWith(token)
            }, { e ->
                Logs.d(this@GitHubAuthProvider, "github user error $e")
            })
    }

    protected abstract fun signInWith(token: String)

    override val id: String
        get() = providerId

    companion object {
        const val RC_SIGN_IN = 0xff02
        const val providerId: String = "github.com"
    }
}