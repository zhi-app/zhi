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
    dataBinding {
        isEnabled = true
    }
    flavorDimensions(Config.dimensionChannel)
    productFlavors {
        create(Config.dimensionChannelFirebase) {
            setDimension(Config.dimensionChannel)
        }
        create(Config.dimensionChannelMock) {
            setDimension(Config.dimensionChannel)
        }
    }
}

afterEvaluate {
    tasks.matching {
        it.name.endsWith("BuildConfig")
    }.forEach {
        it.enabled = false
    }
}

dependencies {
    api(project(":service-auth-common"))
    implementation(Config.constraintLayout)
    kapt(Config.daggerCompiler)
    kapt(Config.daggerAndroidProcessor)
}