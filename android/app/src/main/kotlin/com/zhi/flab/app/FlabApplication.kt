package com.zhi.flab.app

import android.app.Application
import com.zhi.common.app.ApplicationModule
import com.zhi.common.util.LogsModule
import com.zhi.flab.flutter.FlutterDIApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

class FlabApplication : FlutterDIApplication() {

    override fun applicationInjector(): AndroidInjector<out Application> {
        return DaggerFlabApplication_FlapApplicationComponent.builder().application(this).build()
    }

    @Singleton
    @Component(
            modules = [
                ApplicationModule::class,
                LogsModule::class
            ]
    )
    interface FlapApplicationComponent : AndroidInjector<FlabApplication> {

        @Component.Builder
        interface Builder {
            @BindsInstance
            fun application(application: Application): Builder

            fun build(): FlapApplicationComponent
        }
    }
}