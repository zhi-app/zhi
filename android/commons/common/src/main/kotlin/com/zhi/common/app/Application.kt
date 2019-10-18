package com.zhi.common.app

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import androidx.annotation.CallSuper
import com.google.errorprone.annotations.FormatMethod
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.multibindings.Multibinds
import javax.inject.Inject
import javax.inject.Qualifier

abstract class BaseApplication : Application() {
    @Inject
    internal lateinit var attachCallbacks: Set<@JvmSuppressWildcards(true) AppAttachCallback>

    @Inject
    internal lateinit var createCallbacks: Set<@JvmSuppressWildcards(true) AppCreateCallback>
    @Inject
    @field:MainProcess
    internal lateinit var createCallback2: Set<@JvmSuppressWildcards(true) AppCreateCallback>

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
                    val applicationInjector = applicationInjector() as AndroidInjector<Application>
                    applicationInjector.inject(this)
                    check(!needToInject) {
                        "The AndroidInjector returned from applicationInjector() did not inject the " + "BaseApplication"
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
            AndroidInjectionModule::class
        ]
)
abstract class ApplicationModule private constructor() {
    @Multibinds
    internal abstract fun attachCallbacks(): Set<AppAttachCallback>

    @Multibinds
    internal abstract fun createCallbacks(): Set<AppCreateCallback>

    @Binds
    @AppContext
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