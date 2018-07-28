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

dependencies {
    api(Config.kotlinStdLib)
    api(Config.guava)

    api(Config.dagger)
    api(Config.daggerAndroid)
    api(Config.daggerAndroidSupport)
    kapt(Config.daggerCompiler)
    kapt(Config.daggerAndroidProcessor)

    api(Config.rxJava)
    api(Config.rxAndroid)
    api(Config.rxKotlin)
    api(Config.rxLifecycle)
    api(Config.rxPermission)

    api(Config.ktxcore)
    api(Config.annotation)
    api(Config.appcompat)
    api(Config.fragment)
    api(Config.material)

    api(Config.browser)

    api(Config.lifecycleRuntime)
    api(Config.lifecycleViewModel)
    api(Config.lifecycleLiveData)
    api(Config.lifecycleExtensions)
    kapt(Config.lifecycleCompiler)

    testImplementation(Config.junit)
    testImplementation(Config.hamcrestCore)
    testImplementation(Config.hamcrestIntegration)
    testImplementation(Config.testRunner)
    testImplementation(Config.testExpresso)
}
