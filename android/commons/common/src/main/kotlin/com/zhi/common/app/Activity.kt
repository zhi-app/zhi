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