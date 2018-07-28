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

import android.os.Looper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.Executor

/**
 * Provides the Observable scheduler.
 */
object Schedulers {

    /**
     * Alias to  [AndroidSchedulers.mainThread]
     */
    fun mainThread(): Scheduler = AndroidSchedulers.mainThread()

    /**
     * Alias to [AndroidSchedulers.from]
     */
    fun from(looper: Looper): Scheduler = AndroidSchedulers.from(looper)

    /**
     * Alias to  [io.reactivex.schedulers.Schedulers.computation]
     */
    fun computation(): Scheduler = io.reactivex.schedulers.Schedulers.computation()

    /**
     * Alias to [io.reactivex.schedulers.Schedulers.io]
     */
    fun io() = io.reactivex.schedulers.Schedulers.io()

    /**
     * Alias to [io.reactivex.schedulers.Schedulers.trampoline]
     */
    fun trampoline(): Scheduler = io.reactivex.schedulers.Schedulers.trampoline()

    /**
     * Alias to [io.reactivex.schedulers.Schedulers.newThread]
     */
    fun newThread(): Scheduler = io.reactivex.schedulers.Schedulers.newThread()

    /**
     * Alia to [io.reactivex.schedulers.Schedulers.single]
     */
    fun single(): Scheduler = io.reactivex.schedulers.Schedulers.single()

    /**
     * Alias to [io.reactivex.schedulers.Schedulers.from]
     */
    fun from(executor: Executor) = io.reactivex.schedulers.Schedulers.from(executor)
}
