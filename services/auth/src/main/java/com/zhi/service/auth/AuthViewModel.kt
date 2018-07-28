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

import android.content.Context
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.ActivityScope
import com.zhi.common.app.AppContext
import com.zhi.common.app.BaseViewModel
import javax.inject.Inject
import javax.inject.Provider

@ActivityScope
class AuthViewModel @Inject
constructor(
    @AppContext context: Context,
    @ActivityContext val authProviders: Map<String, @JvmSuppressWildcards(true) Provider<AuthProvider>>
) : BaseViewModel(context)