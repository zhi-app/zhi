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
android {
    defaultConfig {
        versionCode = 1
        versionName = "1.0"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    api(project(":common"))
    api(project(":common-net"))
    api(Config.glide)
    api(Config.glideOkHttp)
    kapt(Config.glideCompiler)
    kapt(Config.daggerCompiler)
    kapt(Config.daggerAndroidProcessor)
}