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

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.zhi.common.app.AppContext
import com.zhi.common.firebase.FirebaseAppModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        FirebaseAppModule::class,
        FirebaseAuthServiceModule::class
    ]
)
object FirebaseAuthModule {
    @JvmStatic
    @Provides
    @Singleton
    @AppContext
    fun firebaseAuth(@AppContext app: FirebaseApp): FirebaseAuth {
        return FirebaseAuth.getInstance(app)!!
    }
}

@Module
abstract class FirebaseAuthServiceModule {
    @Binds
    @Singleton
    @AppContext
    abstract fun authService(authService: FirebaseAuthService): AuthService
}