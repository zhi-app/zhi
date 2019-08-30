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

package com.zhi.common.app

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.SparseArray
import javax.inject.Inject

typealias HostResultCallback = (resultCode: Int, data: Intent?) -> Unit

class HostResultCallbacks @Inject constructor() {

    private val callbacks = SparseArray<HostResultCallback>(4)

    fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbacks[requestCode]?.also { callback ->
            callbacks.remove(requestCode)
            callback.invoke(resultCode, data)
        }
    }

    fun with(requestCode: Int, callback: HostResultCallback) {
        callbacks.put(requestCode, callback)
    }
}

fun BaseActivity.startForResult(requestCode: Int, callback: HostResultCallback) {
    hostResultCallbacks.takeIf { it.isPresent }?.get()?.with(requestCode, callback)
}

fun BaseFragment.startForResult(requestCode: Int, callback: HostResultCallback) {
    hostResultCallbacks.takeIf { it.isPresent }?.get()?.with(requestCode, callback)
}

fun BaseActivity.startActivityForResult(
    requestCode: Int,
    intent: Intent,
    options: Bundle? = null,
    callback: HostResultCallback
) {
    hostResultCallbacks.takeIf { it.isPresent }?.get()?.with(requestCode, callback)
    startActivityForResult(intent, requestCode, options)
}

fun BaseFragment.startActivityForResult(
    requestCode: Int,
    intent: Intent,
    options: Bundle? = null,
    callback: HostResultCallback
) {
    hostResultCallbacks.takeIf { it.isPresent }?.get()?.with(requestCode, callback)
    startActivityForResult(intent, requestCode, options)
}


fun BaseActivity.startIntentSenderForResult(
    requestCode: Int,
    intent: IntentSender,
    fillInIntent: Intent? = null,
    flagsMask: Int = 0,
    flagsValues: Int = 0,
    extraFlags: Int = 0,
    options: Bundle? = null,
    callback: HostResultCallback
) {
    hostResultCallbacks.takeIf { it.isPresent }?.get()?.with(requestCode, callback)
    startIntentSenderForResult(
        intent,
        requestCode,
        fillInIntent,
        flagsMask,
        flagsValues,
        extraFlags,
        options
    )
}

fun BaseFragment.startIntentSenderForResult(
    requestCode: Int,
    intent: IntentSender,
    fillInIntent: Intent? = null,
    flagsMask: Int = 0,
    flagsValues: Int = 0,
    extraFlags: Int = 0,
    options: Bundle? = null,
    callback: HostResultCallback
) {
    hostResultCallbacks.takeIf { it.isPresent }?.get()?.with(requestCode, callback)
    startIntentSenderForResult(
        intent,
        requestCode,
        fillInIntent,
        flagsMask,
        flagsValues,
        extraFlags,
        options
    )
}