///*
// * Copyright [2018] [zhi]
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.zhi.common.app
//
//import android.content.Context
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.ViewModelProviders
//import dagger.MapKey
//import dagger.Module
//import dagger.Provides
//import dagger.multibindings.Multibinds
//import javax.inject.Inject
//import javax.inject.Provider
//import kotlin.reflect.KClass
//
//open class BaseViewModel
//@Inject constructor(@AppContext val context: Context) : ViewModel()
//
//@MapKey
//@MustBeDocumented
//@kotlin.annotation.Target(
//    AnnotationTarget.FUNCTION,
//    AnnotationTarget.PROPERTY_GETTER,
//    AnnotationTarget.PROPERTY_SETTER
//)
//annotation class ViewModelKey(val value: KClass<out ViewModel>)
//
//@Module
//abstract class ActivityViewModelProviderModule private constructor() {
//
//    @Multibinds
//    @ActivityScope
//    @ActivityContext
//    internal abstract fun viewModels(): Map<Class<out ViewModel>, ViewModel>
//
//    @Module
//    companion object {
//        @JvmStatic
//        @Provides
//        @ActivityScope
//        @ActivityContext
//        fun viewModelProvider(
//            @ActivityContext activity: BaseActivity,
//            @ActivityContext viewModels:
//            Map<Class<out ViewModel>, @JvmSuppressWildcards(true) Provider<ViewModel>>
//        ) = ViewModelProviders.of(activity, object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                @Suppress("UNCHECKED_CAST")
//                return viewModels[modelClass]!!.get() as T
//            }
//        })
//    }
//}
//
//@Module
//abstract class FragmentViewModelProviderModule private constructor() {
//
//    @Multibinds
//    @FragmentScope
//    @FragmentContext
//    internal abstract fun viewModels(): Map<Class<out ViewModel>, ViewModel>
//
//    @Module
//    companion object {
//        @JvmStatic
//        @Provides
//        @FragmentScope
//        @FragmentContext
//        fun viewModelProvider(
//            @FragmentContext fragment: BaseFragment,
//            @FragmentContext viewModels:
//            Map<Class<out ViewModel>, @JvmSuppressWildcards(true) Provider<ViewModel>>
//        ) = ViewModelProviders.of(fragment, object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                @Suppress("UNCHECKED_CAST")
//                return viewModels[modelClass]!!.get() as T
//            }
//        })
//    }
//}