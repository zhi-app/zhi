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
import android.content.Context
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.zhi.common.app.AppContext
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.FragmentContext
import com.zhi.common.app.HostResultCallbacks
import com.zhi.common.util.Logs
import dagger.Lazy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebasePhoneAuthViewModel @Inject
constructor(
    @AppContext context: Context,
    @AppContext private val auth: Lazy<FirebaseAuth>,
    @FragmentContext private val firebaseCredentialsClient: Lazy<CredentialsClient>,
    @FragmentContext private val firebasePhoneAuthProvider: Lazy<PhoneAuthProvider>
) : PhoneAuthViewModel(context) {

    private lateinit var verificationId: String
    private lateinit var token: ForceResendingToken

    override fun showHintPicker(host: BaseFragment) {
        val hintPickerIntent = firebaseCredentialsClient.get().getHintPickerIntent(
            HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build()
        )
        HostResultCallbacks.startIntentSenderForResult(
            host,
            RC_HINT_PICKER,
            hintPickerIntent.intentSender
        ) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                (data?.getParcelableExtra(Credential.EXTRA_KEY) as? Credential)?.run {
                    phoneNumber.set(id)
                }
            }
        }
    }

    override fun verifyPhoneNumber(phoneNumber: String, force: Boolean) {
        firebasePhoneAuthProvider.get().verifyPhoneNumber(
            phoneNumber,
            SMS_AUTO_RETRIEVAL_TIMEOUT_SS,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            object : OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Logs.d(this@FirebasePhoneAuthViewModel, "verify completed $credential")

                    auth.get().signInWithCredential(
                        credential
                    ).addOnSuccessListener {
                        Logs.d(this@FirebasePhoneAuthViewModel, "confirmSMSCode Success")

                    }.addOnFailureListener {
                        Logs.d(this@FirebasePhoneAuthViewModel, "confirmSMSCode Failed")

                    }.addOnCompleteListener {
                        Logs.d(this@FirebasePhoneAuthViewModel, "confirmSMSCode complete")
                    }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Logs.e(this@FirebasePhoneAuthViewModel, "verify failed $e")
                }

                override fun onCodeSent(verificationId: String, token: ForceResendingToken) {
                    super.onCodeSent(verificationId, token)
                    val provider = this@FirebasePhoneAuthViewModel
                    provider.verificationId = verificationId
                    provider.token = token

                    Logs.e(
                        this@FirebasePhoneAuthViewModel,
                        "verify code sent $verificationId, $token"
                    )
                }

                override fun onCodeAutoRetrievalTimeOut(verificationId: String) {
                    super.onCodeAutoRetrievalTimeOut(verificationId)
                    Logs.d(this, "PhoneAuth timeout for $verificationId")
                }
            },
            if (force) token else null
        )
    }

    override fun confirmSMSCode(phoneNumber: String, smsCode: String) {
        auth.get().firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode)
        verifyPhoneNumber(phoneNumber, true)
    }
}