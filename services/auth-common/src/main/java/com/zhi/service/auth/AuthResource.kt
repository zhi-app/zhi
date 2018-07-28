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

package com.zhi.service.auth

import androidx.lifecycle.MutableLiveData
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.ActivityScope
import dagger.Module
import dagger.Provides

data class AuthResource
constructor(
    val state: MutableLiveData<State> = MutableLiveData(),
    var data: Any? = null,
    var exception: Exception? = null
) {

    fun loading(data: Any? = null) {
        this.data = data
        state.value = State.LOADING
    }

    fun success(data: Any) {
        this.data = data
        state.value = State.LOADING
    }

    fun failure(exception: Exception, data: Any? = null) {
        this.data = data
        this.exception = exception
        state.value = State.FAILURE
    }

    enum class State {
        SUCCESS, FAILURE, LOADING
    }
}


@Module
object AuthResourceModule {
    @JvmStatic
    @Provides
    @ActivityScope
    @ActivityContext
    fun authResource() = AuthResource()
}