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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.ActivityScope
import com.zhi.common.app.AppContext
import com.zhi.service.auth.google.R
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module(includes = [GoogleAuthClientModule::class])
abstract class GoogleAuthBaseModule private constructor() {
    @Binds
    @IntoMap
    @ActivityContext
    @StringKey(GoogleAuthProvider.providerId)
    abstract fun authProvider(@ActivityContext authProvider: GoogleAuthProvider): AuthProvider
}

@Module
object GoogleAuthClientModule {
    @JvmStatic
    @Provides
    @ActivityScope
    @ActivityContext
    fun googleSignInOptions(@AppContext context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .build()
    }

    @JvmStatic
    @Provides
    @ActivityScope
    @ActivityContext
    fun googleSignInClient(@AppContext context: Context, @ActivityContext options: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(context, options)
    }
}