import org.gradle.api.JavaVersion

object Config {
    const val versionCode = 1
    const val versionName = "1.0.0"
    /**[home](https://github.com/gradle/gradle)*/
    const val gradleAndroid = "com.android.tools.build:gradle:4.0.0-alpha01"

    /**[home](https://github.com/JetBrains/kotlin)*/
    private const val kotlinVersion = "1.3.60-eap-25"
    const val gradleKotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    /**[source](https://github.com/JetBrains/kotlin)*/
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"

    const val application = "com.android.application"
    const val library = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"

    const val compileSdkVersion = 29
    const val targetSdkVersion = 28
    const val minSdkVersion = 19
    val sourceCompatibility = JavaVersion.VERSION_1_8
    val targetCompatibility = JavaVersion.VERSION_1_8
    const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    private const val ktxVersion = "1.1.0"
    /**[home](https://developer.android.com/kotlin/ktx)*/
    const val junit = "junit:junit:4.12"
    const val hamcrestCore = "org.hamcrest:hamcrest-core:1.3"
    const val hamcrestIntegration = "org.hamcrest:hamcrest-integration:1.3"
    const val testRunner = "androidx.test:runner:1.2.0"
    const val testExpresso = "androidx.test.espresso:espresso-core:3.2.0"

    const val ktxcore = "androidx.core:core-ktx:$ktxVersion"
    const val annotation = "androidx.annotation:annotation:$ktxVersion"
    const val appcompat = "androidx.appcompat:appcompat:$ktxVersion"
    const val fragment = "androidx.fragment:fragment-ktx:$ktxVersion"
    const val sqlite = "androidx.sqlite:sqlite-ktx:$ktxVersion"
    const val collection = "androidx.collection:collection-ktx:$ktxVersion"
    const val platte = "androidx.palette:palette-ktx:$ktxVersion"
    const val workRuntime = "android.arch.work:work-runtime-ktx:1.0.0-alpha10"

    /** [source](https://developer.android.com/studio/build/multidex) */
    const val multidex = "androidx.multidex:multidex:2.0.0"

    /**[Source](https://github.com/google/dagger)*/
    private const val version = "2.25"
    const val dagger = "com.google.dagger:dagger:$version"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:$version"
    const val daggerAndroid = "com.google.dagger:dagger-android:$version"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$version"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:$version"

    /** [source](https://github.com/google/guava) */
    const val guava = "com.google.guava:guava:28.1-android"
}