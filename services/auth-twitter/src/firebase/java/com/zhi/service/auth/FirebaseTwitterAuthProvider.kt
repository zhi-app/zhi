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

import com.google.firebase.auth.FirebaseAuth
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.AppContext
import com.zhi.common.util.Logs
import dagger.Lazy
import javax.inject.Inject

class FirebaseTwitterAuthProvider @Inject
constructor(
    @AppContext private val auth: Lazy<FirebaseAuth>,
    @ActivityContext client: Lazy<TwitterAuthClient>
) : TwitterAuthProvider(client) {

    override fun signInWith(token: String, secret: String) {
        Logs.d(this, "signInWith $token $secret")

        auth.get().signInWithCredential(
            com.google.firebase.auth.TwitterAuthProvider.getCredential(token, secret)
        ).addOnSuccessListener {
            Logs.d(this@FirebaseTwitterAuthProvider, "signInWith Success")

        }.addOnFailureListener {
            Logs.d(this@FirebaseTwitterAuthProvider, "signInWith Failed")

        }.addOnCompleteListener {
            Logs.d(this@FirebaseTwitterAuthProvider, "signInWith complete")
        }
    }
}