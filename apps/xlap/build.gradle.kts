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
        applicationId = "com.zhi.app.xlap"
        versionCode = Config.gitVersionCode
        versionName = "0.1.0.${Config.gitVersionTag}"

        resValue("bool", "neo_bugly_enable_debug", "true")
        resValue("integer", "common_max_enabled_log_level", "10")
        resValue("string", "common_log_prefix", "xlap")
        resValue("string", "common_file_provider_authority", "$applicationId.fileProviders")
    }
    signingConfigs {
        //        configurations["debug"]
//        getByName("debug") {
//            storePassword = "zhiapp"
//            keyPassword = "zhiapp"
//            keyAlias = "zhiapp"
//            storeFile = file("${rootProject.rootDir}/extras/keystores/zhiapp.jks")
//        }
    }

    flavorDimensions(Config.dimensionApi, Config.dimensionChannel)
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
        create(Config.dimensionChannelFirebase) {
            setDimension(Config.dimensionChannel)
            versionCode = Config.channelVersionCode(Config.dimensionChannelFirebase, versionCode)
            versionNameSuffix = "-${Config.dimensionChannelFirebase}"
        }
        create(Config.dimensionChannelMock) {
            setDimension(Config.dimensionChannel)
            versionCode = Config.channelVersionCode(Config.dimensionChannelMock, versionCode)
            versionNameSuffix = "-${Config.dimensionChannelMock}"
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-net"))
    implementation(project(":common-multidex"))
    implementation(project(":common-glide"))
    implementation(project(":service-neo"))
    implementation(project(":service-auth"))
    implementation(project(":module-splash"))
    implementation(Config.constraintLayout)
    implementation(Config.navigationFragment)
    implementation(Config.navigationUI)
    kapt(Config.daggerCompiler)
    kapt(Config.daggerAndroidProcessor)
}

apply(plugin = Config.googleService)