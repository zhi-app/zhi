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

package com.zhi.common.glide

import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Scope

/**
 * Indicate that the injected var should be contained in Activity.
 */
@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class GlideScope

@Subcomponent(modules = [GlideConfigsModule::class])
@GlideScope
interface GlideComponent : AndroidInjector<GlideConfigs> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<GlideConfigs>()
}
