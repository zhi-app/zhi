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

package com.zhi.module.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.BaseActivity
import com.zhi.common.rx.LifecycleProvider
import com.zhi.common.rx.Schedulers
import com.zhi.common.util.Logs
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    @Inject
    @field:ActivityContext
    lateinit var lifecycleProvider: LifecycleProvider

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.timer(5000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.mainThread())
            .compose(lifecycleProvider.bindUntilEvent<Long>(Lifecycle.Event.ON_DESTROY))
            .subscribe {
                Logs.d(this, "Current time is $it")
                Logs.wtf(this, "Current time is $it")
                finish()
            }
    }
}

