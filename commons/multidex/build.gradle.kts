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
import com.android.build.gradle.internal.packaging.createDefaultDebugStore
import com.android.builder.model.ProductFlavor

android {
    defaultConfig {
        versionCode = 1
        versionName = "1.0"
    }
    flavorDimensions(Config.dimensionApi)
    productFlavors {
        create(Config.dimensionApi19) {
            setDimension(Config.dimensionApi)
            minSdkVersion(19)
            versionCode = Config.apiVersionCode(19, versionCode)
            versionNameSuffix = "-${Config.dimensionApi19}"
        }
        create(Config.dimensionApi21) {
            setDimension(Config.dimensionApi)
            minSdkVersion(21)
            versionCode = Config.apiVersionCode(21, versionCode)
            versionNameSuffix = "-${Config.dimensionApi21}"
            matchingFallbacks = listOf(Config.dimensionApi19)
        }
    }
}

dependencies {
    api(project(":common"))
    implementation(Config.multidex)
    kapt(Config.daggerCompiler)
    kapt(Config.daggerAndroidProcessor)

    testImplementation(Config.junit)
    testImplementation(Config.hamcrestCore)
    testImplementation(Config.hamcrestIntegration)
    testImplementation(Config.testRunner)
    testImplementation(Config.testExpresso)
}