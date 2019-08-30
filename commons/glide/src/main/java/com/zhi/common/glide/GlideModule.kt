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

package com.zhi.common.glide

import android.app.Application
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.ActivityScope
import com.zhi.common.app.AppCreateCallback
import com.zhi.common.app.BaseActivity
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.FragmentContext
import com.zhi.common.app.FragmentScope
import com.zhi.common.app.MainProcess
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import io.reactivex.Observable
import javax.inject.Provider

@Module(subcomponents = [GlideComponent::class])
object GlideModule {
    @JvmStatic
    @Provides
    @IntoSet
    @MainProcess
    fun appCreateGlideCallback(glideComponentBuilderProvider: Provider<GlideComponent.Builder>): AppCreateCallback {
        return object : AppCreateCallback {
            override fun onCreate(application: Application) {
                glideComponentBuilderProvider.get().create(GlideAppModule.configs)
            }
        }
    }
}

@Module
object ActivityGlideRequestsModule {
    @JvmStatic
    @Provides
    @ActivityScope
    @ActivityContext
    fun glideRequests(activity: BaseActivity): GlideRequests = Glide.with(activity)
}

@Module
object FragmentGlideRequestsModule {
    @JvmStatic
    @Provides
    @FragmentScope
    @FragmentContext
    fun glideRequests(fragment: BaseFragment): GlideRequests = Glide.with(fragment)
}
