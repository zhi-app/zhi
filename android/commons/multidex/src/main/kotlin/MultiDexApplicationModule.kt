package com.zhi.common.flutter

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.zhi.common.app.AppAttachCallback
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Inject
import javax.inject.Singleton

@Module
abstract class MultiDexApplicationModule private constructor() {
    @Binds
    @IntoSet
    @Singleton
    internal abstract fun appAttachCallback(support: MultiDexApplicationSupport): AppAttachCallback
}

internal class MultiDexApplicationSupport @Inject constructor() : AppAttachCallback {
    override fun onAttach(base: Context, application: Application) {
        MultiDex.install(application)
    }
}
