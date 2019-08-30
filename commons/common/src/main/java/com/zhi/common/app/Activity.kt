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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.common.base.Optional
import dagger.Binds
import dagger.BindsOptionalOf
import dagger.Module
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {
    @Inject
    @field:ActivityContext
    lateinit var optionalInjector: Optional<AndroidInjector<Fragment>>
    @Inject
    @field:ActivityContext
    lateinit var hostResultCallbacks: Optional<HostResultCallbacks>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        hostResultCallbacks.takeIf { it.isPresent }?.get()?.onResult(requestCode, resultCode, data)
    }
}

@Module
abstract class ActivityModule private constructor() {

    @Binds
    @ActivityScope
    @ActivityContext
    abstract fun activityContext(activity: BaseActivity): Context

    @BindsOptionalOf
    @ActivityContext
    abstract fun fragmentInjector(): AndroidInjector<Fragment>

    @BindsOptionalOf
    @ActivityContext
    abstract fun hostResultCallbacks(): HostResultCallbacks
}

@Module
abstract class ActivityFragmentInjectorModule private constructor() {
    @Binds
    @ActivityScope
    @ActivityContext
    abstract fun fragmentInjector(injector: DispatchingAndroidInjector<Fragment>): AndroidInjector<Fragment>
}

@Module
abstract class ActivityHostCallbackModule private constructor() {

    @Binds
    @ActivityScope
    @ActivityContext
    abstract fun hostCallback(callback: HostResultCallbacks): HostResultCallbacks
}