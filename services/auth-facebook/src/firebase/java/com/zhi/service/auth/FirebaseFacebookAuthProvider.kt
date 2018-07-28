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
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.AppContext
import com.zhi.common.util.Logs
import dagger.Lazy
import javax.inject.Inject

class FirebaseFacebookAuthProvider @Inject
constructor(
    @AppContext private val auth: Lazy<FirebaseAuth>,
    @ActivityContext authResource: Lazy<AuthResource>
) : FacebookAuthProvider(authResource) {

    override fun signInWith(token: String) {
        Logs.d(this@FirebaseFacebookAuthProvider, "signInWith $token")
        auth.get().signInWithCredential(
            com.google.firebase.auth.FacebookAuthProvider.getCredential(token)

        ).addOnSuccessListener {
            Logs.d(this@FirebaseFacebookAuthProvider, "signInWithCredential Success")

        }.addOnFailureListener {
            Logs.d(this@FirebaseFacebookAuthProvider, "signInWithCredential Failed")

        }.addOnCompleteListener {
            Logs.d(this@FirebaseFacebookAuthProvider, "signInWithCredential complete")
        }
    }
}