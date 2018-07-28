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
        applicationId = "com.zhi.app.cupid"
        versionCode = Config.gitVersionCode
        versionName = "0.1.0.${Config.gitVersionTag}"

        resValue("bool", "neo_bugly_enable_debug", "true")
        resValue("integer", "common_max_enabled_log_level", "10")
        resValue("string", "common_log_prefix", "cupid")
        resValue("string", "common_file_provider_authority", "$applicationId.fileProviders")
    }

    flavorDimensions(Config.dimensionMinApi, Config.dimensionChannel)
    productFlavors {
        create(Config.minApi19) {
            dimension = Config.dimensionMinApi
            minSdkVersion(19)
            versionCode = versionCode?.plus(19 * 1000)
            versionNameSuffix = "-${Config.minApi19}"
        }
        create(Config.minApi21) {
            dimension = Config.dimensionMinApi
            minSdkVersion(21)
            versionCode = versionCode?.plus(21 * 1000)
            versionNameSuffix = "-${Config.minApi21}"
            matchingFallbacks = listOf(Config.minApi19)
        }
        create(Config.googlePlay) {
            dimension = Config.dimensionChannel
            versionNameSuffix = "-${Config.googlePlay}"
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-net"))
    implementation(project(":common-multidex"))
    implementation(project(":common-image"))
    implementation(project(":service-neo"))
    implementation(project(":module-splash"))
    implementation(Config.constraintLayout)
    kapt(Config.daggerCompiler)
    kapt(Config.daggerAndroidProcessor)
}