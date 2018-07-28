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

package com.zhi.common.rx

import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.ActivityScope
import com.zhi.common.app.BaseActivity
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.FragmentContext
import com.zhi.common.app.FragmentScope
import dagger.Module
import dagger.Provides

class Permissions : RxPermissions {
    constructor(activity: BaseActivity) : super(activity)
    constructor(fragment: BaseFragment) : super(fragment)
}

@Module
object ActivityPermissionModule {
    @JvmStatic
    @Provides
    @ActivityScope
    @ActivityContext
    fun permissions(@ActivityContext activity: BaseActivity): Permissions =
        Permissions(activity)
}

@Module
object FragmentPermissionModule {
    @JvmStatic
    @Provides
    @FragmentScope
    @FragmentContext
    internal fun permissions(@FragmentContext fragment: BaseFragment): Permissions =
        Permissions(fragment)
}
