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

import androidx.annotation.CheckResult
import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.ActivityScope
import com.zhi.common.app.BaseActivity
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.FragmentContext
import com.zhi.common.app.FragmentScope
import dagger.Binds
import dagger.Module
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

interface LifecycleProvider : com.trello.rxlifecycle2.LifecycleProvider<Event>

internal class LifecycleAdapter @Inject constructor(source: LifecycleOwner) :
    LifecycleProvider, LifecycleObserver {

    private val lifecycleSubject = BehaviorSubject.create<Event>()

    init {
        source.lifecycle.addObserver(this)
    }

    @NonNull
    @CheckResult
    override fun lifecycle(): Observable<Event> = lifecycleSubject.hide()

    @NonNull
    @CheckResult
    override fun <T> bindUntilEvent(event: Event): LifecycleTransformer<T> =
        RxLifecycle.bindUntilEvent(lifecycleSubject, event)

    @NonNull
    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> =
        RxLifecycle.bind(lifecycleSubject, AUTO_DETECT_EVENT)

    @OnLifecycleEvent(Event.ON_ANY)
    fun onAnyEvent(source: LifecycleOwner, event: Event) =
        lifecycleSubject.onNext(event)

    companion object {
        private val AUTO_DETECT_EVENT = Function<Event, Event> { event ->
            when (event) {
                Event.ON_CREATE -> Event.ON_DESTROY
                Event.ON_START -> Event.ON_STOP
                Event.ON_RESUME -> Event.ON_PAUSE
                Event.ON_PAUSE -> Event.ON_STOP
                Event.ON_STOP -> Event.ON_DESTROY
                Event.ON_DESTROY, Event.ON_ANY -> event
                else -> event
            }
        }
    }
}

@Module
abstract class ActivityLifecycleModule private constructor() {

    @Binds
    @ActivityScope
    internal abstract fun lifecycleOwner(@ActivityContext activity: BaseActivity): LifecycleOwner

    @Binds
    @ActivityScope
    @ActivityContext
    internal abstract fun lifecycleProvider(adapter: LifecycleAdapter): LifecycleProvider
}

@Module
abstract class FragmentLifecycleModule private constructor() {
    @Binds
    @FragmentScope
    internal abstract fun lifecycleOwner(@FragmentContext fragment: BaseFragment): LifecycleOwner

    @Binds
    @FragmentScope
    @FragmentContext
    internal abstract fun lifecycleProvider(adapter: LifecycleAdapter): LifecycleProvider
}
