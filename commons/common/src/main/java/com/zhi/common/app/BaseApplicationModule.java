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

package com.zhi.common.app;

import android.app.Application;
import android.content.Context;

import java.util.Set;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.multibindings.Multibinds;

/**
 * Provides {@link BaseApplication} needs objects.
 */
@Module(includes = {
        AndroidSupportInjectionModule.class
})
public abstract class BaseApplicationModule {

    @Multibinds
    @Singleton
    abstract Set<AppAttachCallback> bindsAttachCallbacks();

    @Multibinds
    @Singleton
    abstract Set<AppCreateCallback> bindsCreateCallbacks();

    @Binds
    @AppContext
    abstract Context bindsContext(Application application);

    private BaseApplicationModule() {
        // LEFT-DO-NOTHING
    }
}