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

package com.zhi.app.cupid

import android.app.Application
import com.zhi.app.common.image.GlideModule
import com.zhi.common.app.ApplicationModule
import com.zhi.common.app.BaseApplication
import com.zhi.common.multidex.MultiDexModule
import com.zhi.module.splash.SplashModule
import com.zhi.service.neo.NeoModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

class CupidApplication : BaseApplication() {

    override fun applicationInjector(): AndroidInjector<out Application> {
        return DaggerCupidApplicationComponent.builder().application(this).build()
    }
}

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        GlideModule::class,
        MultiDexModule::class,
        NeoModule::class,
        SplashModule::class
    ]
)
interface CupidApplicationComponent : AndroidInjector<CupidApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CupidApplicationComponent
    }
}