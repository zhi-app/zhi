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
import android.app.ActivityManager
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ContentProvider
import android.content.Context
import androidx.annotation.CallSuper
import com.google.errorprone.annotations.FormatMethod
import com.zhi.common.util.FileProvidersModule
import com.zhi.common.util.LogsModule
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasBroadcastReceiverInjector
import dagger.android.HasContentProviderInjector
import dagger.android.HasServiceInjector
import dagger.multibindings.Multibinds
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

abstract class BaseApplication : Application(),
    HasActivityInjector, HasServiceInjector,
    HasBroadcastReceiverInjector, HasContentProviderInjector {

    @Inject
    internal lateinit var activityInjector:
            DispatchingAndroidInjector<@JvmSuppressWildcards(true) Activity>
    @Inject
    internal lateinit var serviceInjector:
            DispatchingAndroidInjector<@JvmSuppressWildcards(true) Service>
    @Inject
    internal lateinit var broadcastReceiverInjector:
            DispatchingAndroidInjector<@JvmSuppressWildcards(true) BroadcastReceiver>
    @Inject
    internal lateinit var contentProviderInjector:
            DispatchingAndroidInjector<@JvmSuppressWildcards(true) ContentProvider>
    @Inject
    internal lateinit var attachCallbacks:
            Set<@JvmSuppressWildcards(true) AppAttachCallback>
    @Inject
    internal lateinit var createCallbacks:
            Set<@JvmSuppressWildcards(true) AppCreateCallback>
    @Inject
    @field:MainProcess
    internal lateinit var createCallback2:
            Set<@JvmSuppressWildcards(true) AppCreateCallback>

    @Volatile
    private var needToInject = true

    @CallSuper
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        injectIfNecessary()
        attachCallbacks.forEach { it.onAttach(base, this) }
        attachCallbacks = emptySet()
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        injectIfNecessary()
        if (isMainProcess(this)) {
            createCallbacks.forEach { it.onCreate(this) }
            createCallbacks = emptySet()
        }
        createCallback2.forEach { it.onCreate(this) }
        createCallback2 = emptySet()
    }

    @Inject
    internal fun setInjected() {
        needToInject = false
    }

    /**
     * Implementations should return an [AndroidInjector] for the concrete [Application].
     * Typically, that injector is a [dagger.Component].
     */
    @FormatMethod
    protected abstract fun applicationInjector(): AndroidInjector<out Application>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = activityInjector

    override fun serviceInjector(): DispatchingAndroidInjector<Service> = serviceInjector

    override fun broadcastReceiverInjector(): DispatchingAndroidInjector<BroadcastReceiver> =
        broadcastReceiverInjector

    // injectIfNecessary is called here but not on the other *Injector() methods because it is the
    // only one that should be called (in AndroidInjection.inject(ContentProvider)) before Application.onCreate()
    override fun contentProviderInjector(): AndroidInjector<ContentProvider> {
        injectIfNecessary()
        return contentProviderInjector
    }

    private fun isMainProcess(context: Context): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        am?.runningAppProcesses?.forEach {
            if (android.os.Process.myPid() == it.pid) {
                return it.processName == context.packageName
            }
        }
        return false
    }

    /**
     * Lazily injects the [Application]'s members. Injection cannot be performed in
     * [Application.onCreate] since [android.content.ContentProvider]s'[android.content.ContentProvider.onCreate]
     * method will be called first and might need injected members on the application.
     * Injection is not performed in the constructor, as that may result in members-injection methods
     * being called before the constructor has completed, allowing for a partially-constructed instance to escape.
     */
    private fun injectIfNecessary() {
        if (needToInject) {
            synchronized(this) {
                if (needToInject) {
                    @Suppress("UNCHECKED_CAST")
                    val applicationInjector =
                        applicationInjector() as AndroidInjector<Application>
                    applicationInjector.inject(this)
                    if (needToInject) {
                        throw IllegalStateException(
                            "The AndroidInjector returned from applicationInjector() did not inject the " + "BaseApplication"
                        )
                    }
                }
            }
        }
    }
}

/**
 * Provides [BaseApplication] needs objects.
 */
@Module(
    includes = [
        AndroidInjectionModule::class,
        LogsModule::class,
        FileProvidersModule::class
    ]
)
abstract class ApplicationModule private constructor() {
    @Multibinds
    internal abstract fun attachCallbacks(): Set<AppAttachCallback>

    @Multibinds
    @Singleton
    internal abstract fun createCallbacks(): Set<AppCreateCallback>

    @Binds
    @AppContext
    @Singleton
    abstract fun appContext(application: Application): Context
}

/**
 * Callback to be invoked during [Application.attachBaseContext].
 */
interface AppAttachCallback {
    /**
     * Custom initialized task can be execute here.
     */
    fun onAttach(base: Context, application: Application)
}

/**
 * Callback to be invoked during [Application.onCreate]. Default is called in any process,
 * however, add [MainProcess] can make it be called in main process
 */
interface AppCreateCallback {
    /**
     * Custom initialized task can be execute here.
     */
    fun onCreate(application: Application)
}

/**
 * Indicates that bound object should work in main process.
 */
@Qualifier
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class MainProcess
