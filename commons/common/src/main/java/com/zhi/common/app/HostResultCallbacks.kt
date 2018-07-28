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
import dagger.Binds
import dagger.Module
import javax.inject.Inject


@Module
abstract class ActivityHostCallbackModule private constructor() {
    @Binds
    @ActivityScope
    @ActivityContext
    abstract fun hostCallback(callback: HostResultCallbacks<BaseActivity>): HostResultCallbacks<BaseActivity>
}

@Module
abstract class FragmentHostCallbackModule private constructor() {
    @Binds
    @FragmentScope
    @FragmentContext
    abstract fun hostCallback(callback: HostResultCallbacks<BaseFragment>): HostResultCallbacks<BaseFragment>
}

interface HostResultCallback<in T> {
    fun onResult(host: T, requestCode: Int, resultCode: Int, data: Intent?)
}

class HostResultCallbacks<T> @Inject constructor() : HostResultCallback<T> {

    val resultCallbacks: SparseArray<HostResultCallback<T>> = SparseArray(4)

    override fun onResult(host: T, requestCode: Int, resultCode: Int, data: Intent?) {
        resultCallbacks[requestCode]?.onResult(host, requestCode, resultCode, data)
    }

    companion object {
        fun startForResult(
            host: BaseActivity,
            requestCode: Int,
            callback: (Int, Intent?) -> Unit
        ) {
            host.hostResultCallbacks.takeIf { it.isPresent }?.get()?.resultCallbacks?.run {
                put(requestCode, object : HostResultCallback<BaseActivity> {
                    override fun onResult(
                        host: BaseActivity,
                        requestCode: Int,
                        resultCode: Int,
                        data: Intent?
                    ) {
                        remove(requestCode)
                        callback.invoke(resultCode, data)
                    }
                })
            }
        }

        fun startForResult(
            host: BaseFragment,
            requestCode: Int,
            callback: (Int, Intent?) -> Unit
        ) {
            host.hostResultCallbacks.takeIf { it.isPresent }?.get()?.resultCallbacks?.run {
                put(requestCode, object : HostResultCallback<BaseFragment> {
                    override fun onResult(
                        host: BaseFragment,
                        requestCode: Int,
                        resultCode: Int,
                        data: Intent?
                    ) {
                        remove(requestCode)
                        callback.invoke(resultCode, data)
                    }
                })
            }
        }

        fun startActivityForResult(
            host: BaseActivity,
            requestCode: Int,
            intent: Intent? = null,
            options: Bundle? = null,
            callback: (Int, Intent?) -> Unit
        ) {
            host.hostResultCallbacks.takeIf { it.isPresent }?.get()?.resultCallbacks?.run {
                put(requestCode, object : HostResultCallback<BaseActivity> {
                    override fun onResult(
                        host: BaseActivity,
                        requestCode: Int,
                        resultCode: Int,
                        data: Intent?
                    ) {
                        remove(requestCode)
                        callback.invoke(resultCode, data)
                    }
                })
            }
            intent?.run {
                host.startActivityForResult(this, requestCode, options)
            }
        }

        fun startActivityForResult(
            host: BaseFragment,
            requestCode: Int,
            intent: Intent? = null,
            options: Bundle? = null,
            callback: (Int, Intent?) -> Unit
        ) {
            host.hostResultCallbacks.takeIf { it.isPresent }?.get()?.resultCallbacks?.run {
                put(requestCode, object : HostResultCallback<BaseFragment> {
                    override fun onResult(
                        host: BaseFragment,
                        requestCode: Int,
                        resultCode: Int,
                        data: Intent?
                    ) {
                        remove(requestCode)
                        callback.invoke(resultCode, data)
                    }
                })
            }
            intent?.run {
                host.startActivityForResult(this, requestCode, options)
            }
        }

        fun startIntentSenderForResult(
            host: BaseActivity,
            requestCode: Int,
            intent: IntentSender,
            fillInIntent: Intent? = null,
            flagsMask: Int = 0,
            flagsValues: Int = 0,
            extraFlags: Int = 0,
            options: Bundle? = null,
            callback: (Int, Intent?) -> Unit
        ) {
            host.hostResultCallbacks.takeIf { it.isPresent }?.get()?.resultCallbacks?.run {
                put(requestCode, object : HostResultCallback<BaseActivity> {
                    override fun onResult(
                        host: BaseActivity,
                        requestCode: Int,
                        resultCode: Int,
                        data: Intent?
                    ) {
                        remove(requestCode)
                        callback.invoke(resultCode, data)
                    }

                })
            }
            host.startIntentSenderForResult(
                intent,
                requestCode,
                fillInIntent,
                flagsMask,
                flagsValues,
                extraFlags,
                options
            )
        }

        fun startIntentSenderForResult(
            host: BaseFragment,
            requestCode: Int,
            intent: IntentSender,
            fillInIntent: Intent? = null,
            flagsMask: Int = 0,
            flagsValues: Int = 0,
            extraFlags: Int = 0,
            options: Bundle? = null,
            callback: (Int, Intent?) -> Unit
        ) {
            host.hostResultCallbacks.takeIf { it.isPresent }?.get()?.resultCallbacks?.run {
                put(requestCode, object : HostResultCallback<BaseFragment> {
                    override fun onResult(
                        host: BaseFragment,
                        requestCode: Int,
                        resultCode: Int,
                        data: Intent?
                    ) {
                        remove(requestCode)
                        callback.invoke(resultCode, data)
                    }

                })
            }
            host.startIntentSenderForResult(
                intent,
                requestCode,
                fillInIntent,
                flagsMask,
                flagsValues,
                extraFlags,
                options
            )
        }
    }
}
