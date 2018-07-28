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
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.firebase.auth.FirebaseAuth
import com.zhi.common.app.ActivityContext
import com.zhi.common.app.ActivityFragmentInjectorModule
import com.zhi.common.app.ActivityModule
import com.zhi.common.app.ActivityScope
import com.zhi.common.app.AppContext
import com.zhi.common.app.BaseActivity
import com.zhi.common.app.BaseFragment
import com.zhi.common.app.FragmentContext
import com.zhi.common.app.FragmentHostCallbackModule
import com.zhi.common.app.FragmentModule
import com.zhi.common.app.FragmentScope
import com.zhi.common.app.FragmentViewModelProviderModule
import com.zhi.common.app.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PhoneAuthModule private constructor() {
    @ActivityScope
    @ContributesAndroidInjector(modules = [PhoneAuthActivityModule::class])
    abstract fun phoneAuthActivity(): PhoneAuthActivity
}

@Module(
    includes = [
        ActivityModule::class,
        ActivityFragmentInjectorModule::class
    ]
)
abstract class PhoneAuthActivityModule private constructor() {
    @Binds
    @ActivityScope
    @ActivityContext
    abstract fun phoneAuthActivity(phoneAuthActivity: PhoneAuthActivity): BaseActivity

    @FragmentScope
    @ContributesAndroidInjector(modules = [PhoneAuthFragmentModule::class])
    abstract fun phoneAuthFragment(): PhoneAuthFragment
}

@Module(
    includes = [
        FragmentModule::class,
        FragmentViewModelProviderModule::class,
        FragmentHostCallbackModule::class
    ]
)
abstract class PhoneAuthFragmentModule private constructor() {
    @Binds
    @FragmentScope
    @FragmentContext
    abstract fun phoneAuthFragment(authFragment: PhoneAuthFragment): BaseFragment

    @Binds
    @IntoMap
    @FragmentContext
    @ViewModelKey(PhoneAuthViewModel::class)
    abstract fun viewModel(authViewModel: FirebasePhoneAuthViewModel): ViewModel

    @Module
    companion object {
        @JvmStatic
        @Provides
        @FragmentScope
        @FragmentContext
        fun firebasePhoneAuthProvider(@AppContext auth: FirebaseAuth) =
            com.google.firebase.auth.PhoneAuthProvider.getInstance(auth)

        @JvmStatic
        @Provides
        @FragmentScope
        @FragmentContext
        fun firebaseCredentialClient(@AppContext context: Context) =
            Credentials.getClient(context)
    }
}