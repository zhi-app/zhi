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

import android.content.Intent
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.HostResultCallbacks
import javax.inject.Inject

class EmailAuthProvider @Inject constructor() : AuthProvider {

    override fun signIn(host: BaseFragment) {
        super.signIn(host)

        HostResultCallbacks.startActivityForResult(
            host,
            RC_SIGN_IN,
            Intent(host.context, EmailAuthActivity::class.java)
        ) { resultCode, data ->

        }
    }

    override val id: String
        get() = providerId

    companion object {
        const val providerId: String = "Email"
        private const val RC_SIGN_IN = 0xff03
    }
}