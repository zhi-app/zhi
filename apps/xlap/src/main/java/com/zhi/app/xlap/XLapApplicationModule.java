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

package com.zhi.app.xlap;

import com.zhi.common.multidex.MultiDexModule;
import com.zhi.module.profile.ProfileModule;
import com.zhi.module.splash.SplashModule;
import com.zhi.service.auth.AuthModule;
import com.zhi.service.neo.NeoModule;

import dagger.Module;

@Module(includes = {
        MultiDexModule.class,
        NeoModule.class,
        SplashModule.class,
        AuthModule.class,
        ProfileModule.class
})
abstract class XLapApplicationModule {

    private XLapApplicationModule() {
        // LEFT-DO-NOTHING
    }
}
