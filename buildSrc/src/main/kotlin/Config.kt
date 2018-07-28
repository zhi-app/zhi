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
import org.gradle.api.JavaVersion
import java.io.BufferedReader
import java.io.InputStreamReader

object Config {
    // -------------- [START] PLUGINS --------------
    /**[home](https://github.com/gradle/gradle)*/
    const val gradleAndroid = "com.android.tools.build:gradle:3.4.0-alpha09"
    /**[home](https://github.com/JetBrains/kotlin)*/
    private const val kotlinVersion = "1.3.11"
    const val gradleKotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    /**[source](https://developers.google.com/android/guides/google-services-plugin#processing_the_json_file)*/
    const val google = "com.google.gms:google-services:4.2.0"
    /** Android Navigation TypeSafe Arguments Gradle Plugin
     * [The Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)*/
    const val gradleNavSafeArgs =
        "android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0-alpha08"

    /**[source](https://github.com/JetBrains/kotlin)*/
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val application = "com.android.application"
    const val library = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"
    const val navigationSafeargs = "androidx.navigation.safeargs"
    const val googleService = "com.google.gms.google-services"
    /** [source](https://ktlint.github.io/) */
    const val kotlinLintVersion = "0.29.0"
    const val spotless = "com.diffplug.gradle.spotless"
    const val spotlessVersion = "3.15.0"

    // -------------- [START] SDKS AND JVM  --------------
    val gitVersionCode = gitVersionCode()
    val gitVersionTag = gitVersionTag()
    const val compileSdkVersion = 28
    const val targetSdkVersion = 28
    const val minSdkVersion = 19
    const val buildToolsVersion = "28.0.3"
    val sourceCompatibility = JavaVersion.VERSION_1_8
    val targetCompatibility = JavaVersion.VERSION_1_8
    const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    // -------------- [START] MISC CONFIG  --------------
    const val dimensionChannel = "channel"
    const val dimensionChannelMock = "mock"
    const val dimensionChannelFirebase = "firebase"

    const val dimensionApi = "api"
    const val dimensionApi19 = "api19"
    const val dimensionApi21 = "api21"

    fun apiVersionCode(api: Int, version: Int?) =
        api * 1_0000 + (version ?: gitVersionCode)

    fun channelVersionCode(channel: String, version: Int?) = when (channel) {
        dimensionChannelFirebase -> 1 * 10_0000 + (version ?: gitVersionCode)
        dimensionChannelMock -> 2 * 10_0000 + (version ?: gitVersionCode)
        else -> (version ?: gitVersionCode)
    }

    // -------------- [START] TEST  --------------
    /**[home](https://developer.android.com/kotlin/ktx)*/
    private const val ktxVersion = "1.0.0"
    const val junit = "junit:junit:4.12"
    const val hamcrestCore = "org.hamcrest:hamcrest-core:1.3"
    const val hamcrestIntegration = "org.hamcrest:hamcrest-integration:1.3"
    const val testRunner = "androidx.test:runner:$ktxVersion"
    const val testExpresso = "androidx.test.espresso:espresso-core:3.1.0-alpha4"

    // -------------- [START] Dependencies  --------------
    const val ktxcore = "androidx.core:core-ktx:$ktxVersion"
    const val annotation = "androidx.annotation:annotation:$ktxVersion"
    const val appcompat = "androidx.appcompat:appcompat:$ktxVersion"
    const val fragment = "androidx.fragment:fragment-ktx:$ktxVersion"
    const val sqlite = "androidx.sqlite:sqlite-ktx:$ktxVersion"
    const val collection = "androidx.collection:collection-ktx:$ktxVersion"
    const val platte = "androidx.palette:palette-ktx:$ktxVersion"
    const val workRuntime = "android.arch.work:work-runtime-ktx:1.0.0-alpha10"

    const val anyncLayoutInflater = "androidx.asynclayoutinflater:asynclayoutinflater:$ktxVersion"

    /** [source](https://developer.android.com/reference/com/google/android/material/packages) */
    const val material = "com.google.android.material:material:$ktxVersion"
    /** [source](https://developer.android.com/studio/build/multidex) */
    const val multidex = "androidx.multidex:multidex:2.0.0"
    /** [source](https://developer.android.com/reference/android/support/constraint/packages) */
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-alpha2"
    /** [source](https://developer.android.com/reference/androidx/classes) */
    const val recyclerView = "androidx.recyclerview:recyclerview:$ktxVersion"
    /** [home](https://developer.chrome.com/multidevice/android/customtabs)
     *  [source](https://developer.android.com/reference/android/support/customtabs/package-summary) */
    const val browser = "androidx.browser:browser:$ktxVersion"

    private const val lifecycleVersion = "2.0.0"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:$lifecycleVersion"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:$lifecycleVersion"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion"
    /** [home](https://developer.android.com/topic/libraries/architecture/livedata) */
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata:$lifecycleVersion"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
    const val lifecycleReactiveStreams =
        "androidx.lifecycle:lifecycle-reactivestreams:$lifecycleVersion"

    private const val navigationVersion = "1.0.0-alpha08"
    const val navigationCommon = "android.arch.navigation:navigation-common-ktx:$navigationVersion"
    const val navigationRuntime = "android.arch.navigation:navigation-runtime-ktx$navigationVersion"
    const val navigationFragment =
        "android.arch.navigation:navigation-fragment-ktx:$navigationVersion"
    const val navigationUI = "android.arch.navigation:navigation-ui-ktx:$navigationVersion"
    const val navigationTesting =
        "android.arch.navigation:navigation-testing-ktx:$navigationVersion"


    /** [source](https://github.com/ReactiveX/RxJava) */
    const val rxJava = "io.reactivex.rxjava2:rxjava:2.1.12"
    /** [source](https://github.com/ReactiveX/rxandroid) */
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.0.2"
    /** [source](https://github.com/ReactiveX/rxkotlin) */
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.2.0"
    /** [source](https://github.com/trello/RxLifecycle)*/
    const val rxLifecycle = "com.trello.rxlifecycle2:rxlifecycle:2.2.1"
    /** [source](https://github.com/tbruyelle/RxPermissions) */
    const val rxPermission = "com.github.tbruyelle:rxpermissions:0.10.2"

    /**[Source](https://github.com/google/dagger)*/
    private const val version = "2.20"
    const val dagger = "com.google.dagger:dagger:$version"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:$version"
    const val daggerAndroid = "com.google.dagger:dagger-android:$version"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$version"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:$version"

    /** [source](https://github.com/google/guava) */
    const val guava = "com.google.guava:guava:25.1-android"
    /** [source](https://github.com/square/okhttp) */
    const val okHttp = "com.squareup.okhttp3:okhttp:3.11.0"

    private const val retrofitVersion = "2.4.0"
    /** https://square.github.io/retrofit/ */
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    /** Guava Adapter - An Adapter for adapting Guava ListenableFuture. */
    const val retrofitAdapterGuava = "com.squareup.retrofit2:adapter-guava:$retrofitVersion"
    /** RxJava2 Adapter - An Adapter for adapting RxJava 2.x types. */
    const val retrofitAdapterRx = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
    /** Scala Adapter - An Adapter for adapting Scala Future. */
    const val retrofitAdapterScala = "com.squareup.retrofit2:adapter-scala:$retrofitVersion"
    /** Gson Converter - A Converter which uses Gson for serialization to and from JSON. */
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    /** Jackson Converter - A Converter which uses Jackson for serialization to and from JSON. */
    const val retrofitConverterJackson = "com.squareup.retrofit2:converter-jackson:$retrofitVersion"
    /** Guava Converter - A Converter which supports Guava's Optional<T> by delegating to
    other converters for T and then wrapping it into Optional. */
    const val retrofiltConverterGuava = "com.squareup.retrofit2:converter-guava:$retrofitVersion"
    /** Java 8 Converter - A Converter which supports Java 8's Optional<T> by delegating to
     * other converters for T and then wrapping it into Optional. */
    const val retrofiltConverterJava8 = "com.squareup.retrofit2:converter-java8:$retrofitVersion"
    /** Java Scalars Converter - A Converter which supports converting strings
     * and both primitives and their boxed types to text/plain bodies. */
    const val retrofiltConverterScalars =
        "com.squareup.retrofit2:converter-scalars:$retrofitVersion"
    /**  Google Protocol Buffer Converter - A Converter which uses Protocol Buffer binary serialization. */
    const val retrofiltConverterProtobuf =
        "com.squareup.retrofit2:converter-protobuf:$retrofitVersion"
    /** Wire Converter - A Converter which uses Wire for protocol buffer-compatible serialization. */
    const val retrofiltConverterWire = "com.squareup.retrofit2:converter-wire:$retrofitVersion"

    private const val glideVersion = "4.8.0"
    /** [source](https://github.com/bumptech/glide) */
    const val glide = "com.github.bumptech.glide:glide:$glideVersion"
    const val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"
    const val glideOkHttp = "com.github.bumptech.glide:okhttp3-integration:$glideVersion"

    const val sonar = "com.facebook.sonar:sonar:0.6.13"
    const val bugly = "com.tencent.bugly:crashreport:2.6.6"

    private const val facebookSdkVersion = "4.34.0"
    /** [source](https://developers.facebook.com/docs/facebook-login/) */
    const val facebookLogin = "com.facebook.android:facebook-login:$facebookSdkVersion"
    /** [source](https://developers.facebook.com/docs/sharing/) */
    const val facebookShare = "com.facebook.android:facebook-share:$facebookSdkVersion"
    /** [source](https://github.com/twitter/twitter-kit-android) */
    const val twitterCore = "com.twitter.sdk.android:twitter-core:3.3.0@aar"

    const val firebaseVersion = "16.0.4"
    /** [guide-docs](https://firebase.google.com/docs/guides/) */
    const val firebaseCore = "com.google.firebase:firebase-core:$firebaseVersion"
    /** [guide-docs](https://firebase.google.com/docs/auth/)
    [source-docs](https://firebase.google.com/docs/reference/android/com/google/firebase/auth/package-summary) */
    const val firebaseAuth = "com.google.firebase:firebase-auth:$firebaseVersion"

    const val gmsVersion = "16.0.1"
    /** [guide-docs](https://developers.google.com/identity/sign-in/android/start-integrating)*/
    const val gmsAuth = "com.google.android.gms:play-services-auth:$gmsVersion"
    const val gmsTasks = "com.google.android.gms:play-services-tasks:$gmsVersion"
    const val gmsBase = "com.google.android.gms:play-services-base:16.0.1"
    const val gmsBasement = "com.google.android.gms:play-services-basement:16.0.1"
}

internal fun gitVersionCode(): Int {
    if (System.getenv("CI_BUILD") == null) {
        return 1234567
    }
    val cmd = "git rev-list HEAD --first-parent --count"
    return execute(cmd).toInt()
}

internal fun gitVersionTag(): String {
    if (System.getenv("CI_BUILD") == null) {
        return "0000"
    }
    val cmd = "git describe --tags"
    val tag = execute(cmd)

    val pattern = Regex("-(\\d+)-g")
    val matcher = pattern.find(tag)
    return if (matcher != null) {
        tag.substring(0, matcher.range.first) + "." + matcher.value
    } else {
        tag + "000"
    }
}

private fun execute(cmd: String): String {
    val process = Runtime.getRuntime().exec(cmd)
    BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
        val res = StringBuilder()
        val buf = CharArray(8192)
        var count: Int
        while (true) {
            count = reader.read(buf)
            if (count == -1) {
                break
            }
            res.append(buf, 0, count)
        }
        return res.trim().toString()
    }
}