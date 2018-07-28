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
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.firebase.auth.FirebaseAuth
import com.zhi.common.app.AppContext
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.FragmentContext
import com.zhi.common.util.Logs
import dagger.Lazy
import javax.inject.Inject

class FirebaseEmailAuthViewModel @Inject
constructor(
    @AppContext context: Context,
    @AppContext private val auth: Lazy<FirebaseAuth>,
    @FragmentContext private val firebaseCredentialsClient: Lazy<CredentialsClient>
) : EmailAuthViewModel(context) {
    override fun showHintPicker(host: BaseFragment) {
    }

    override fun sendEmail(email: String, password: String) {
        auth.get().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Logs.d(this, "signIn user complete")
            }
    }

    override fun createEmail(email: String, password: String) {
        auth.get().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Logs.d(this, "signUp user complete")
            }
    }
}
