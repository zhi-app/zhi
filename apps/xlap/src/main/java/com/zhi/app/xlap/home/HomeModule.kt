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

package com.zhi.app.xlap.home

import androidx.lifecycle.ViewModel
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.ActivityModule
import com.zhi.common.app.ActivityScope
import com.zhi.common.app.ActivityViewModelProviderModule
import com.zhi.common.app.BaseActivity
import com.zhi.common.app.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule private constructor() {

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun homeActivity(): HomeActivity
}

@Module(
    includes = [
        ActivityModule::class,
        ActivityViewModelProviderModule::class
    ]
)
abstract class HomeActivityModule private constructor() {
    @Binds
    @ActivityScope
    @ActivityContext
    abstract fun homeActivity(homeActivity: HomeActivity): BaseActivity

    @Binds
    @IntoMap
    @ActivityContext
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel
}
