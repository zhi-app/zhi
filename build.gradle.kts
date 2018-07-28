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
import com.android.build.gradle.BaseExtension

buildscript {
    repositories {
        google()
        jcenter()
        mavenLocal()
    }

    dependencies {
        classpath(Config.gradleAndroid)
        classpath(Config.gradleKotlin)
        classpath(Config.gradleNavSafeArgs)
        classpath(Config.google)
    }
}

plugins {
    id(Config.spotless) version Config.spotlessVersion
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenLocal()
        maven {
            setUrl("https://jitpack.io")
        }
        if ((group as String).isNotEmpty() && name != "lint" && name != "internal") {
            configAndroid()
        }
    }
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint(Config.kotlinLintVersion)
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint(Config.kotlinLintVersion)
    }
    java {
        target("**/*.java")
        googleJavaFormat().aosp()
        // you can then layer other format steps, such as
        trimTrailingWhitespace()
        removeUnusedImports()
    }
    groovyGradle {
        target("*.gradle")
        paddedCell()
        trimTrailingWhitespace()
        bumpThisNumberIfACustomStepChanges(3)
        greclipse().configFile(
            "${rootProject.projectDir}/spotless/spotless.eclipseformat.xml",
            "${rootProject.projectDir}/spotless/spotless.groovyformat.prefs"
        )
    }
    format("misc") {
        target("**/*.gradle", "**/*.md", "**/.gitignore")
        trimTrailingWhitespace()
        indentWithSpaces(4)
        endWithNewline()
    }
}

fun Project.configAndroid() {
    val isApp = name.startsWith("app-")
    if (isApp) {
        apply(plugin = Config.application)
    } else {
        apply(plugin = Config.library)
    }
    apply(plugin = Config.kotlinAndroid)
    apply(plugin = Config.kotlinAndroidExtensions)
    apply(plugin = Config.kotlinKapt)
    apply(plugin = Config.navigationSafeargs)

    configure<BaseExtension> {
        compileSdkVersion(Config.compileSdkVersion)

        defaultConfig {
            minSdkVersion(Config.minSdkVersion)
            targetSdkVersion(Config.targetSdkVersion)
            testInstrumentationRunner = Config.instrumentationRunner
            if (isApp) {
                multiDexEnabled = true
            }
        }

        if (isApp) {
            dataBinding {
                isEnabled = true
            }
        }

        lintOptions {
            isCheckAllWarnings = true
            isWarningsAsErrors = true
            isAbortOnError = true
        }

        compileOptions {
            sourceCompatibility = Config.sourceCompatibility
            targetCompatibility = Config.targetCompatibility
        }
    }

    if (!isApp) {
        afterEvaluate {
            tasks.matching {
                it.name.endsWith("BuildConfig")
            }.forEach { it -> it.enabled = false }
        }
    }
}