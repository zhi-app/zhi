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

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.common.base.Optional
import dagger.Binds
import dagger.BindsOptionalOf
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

open class BaseFragment : Fragment() {
    @Inject
    @field:FragmentContext
    lateinit var optionalInjector: Optional<AndroidInjector<Fragment>>
    @Inject
    @field:FragmentContext
    lateinit var hostResultCallbacks: Optional<HostResultCallbacks>

    override fun onAttach(context: Context) {
        findInjector(this)?.inject(this)
        super.onAttach(context)
    }

    private fun findInjector(cur: Fragment): AndroidInjector<Fragment>? {
        // check ancestor's fragment
        var fragment: Fragment? = cur
        do {
            val parent = fragment?.parentFragment
            if (parent is BaseFragment && parent.optionalInjector.isPresent) {
                return parent.optionalInjector.get()
            }
            fragment = parent
        } while (fragment != null)

        // check ancestor's activity
        var activity: Activity? = cur.activity
        do {
            if (activity is BaseActivity &&
                activity.optionalInjector.isPresent
            ) {
                return activity.optionalInjector.get()
            }
            activity = activity?.parent
        } while (activity != null)

        // default return null
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        hostResultCallbacks.takeIf { it.isPresent }?.get()
            ?.onResult(requestCode, resultCode, data)
    }
}

@Module
abstract class FragmentModule private constructor() {
    @BindsOptionalOf
    @FragmentContext
    abstract fun fragmentInjector(): AndroidInjector<Fragment>

    @BindsOptionalOf
    @FragmentContext
    abstract fun hostResultCallbacks(): HostResultCallbacks
}

@Module
abstract class FragmentFragmentInjectorModule private constructor() {
    @Binds
    @ActivityScope
    @ActivityContext
    abstract fun fragmentInjector(injector: DispatchingAndroidInjector<Fragment>): AndroidInjector<Fragment>
}

@Module
abstract class FragmentHostCallbackModule private constructor() {

    @Binds
    @FragmentScope
    @FragmentContext
    abstract fun hostCallback(callback: HostResultCallbacks): HostResultCallbacks
}