buildscript {
    repositories {
        google()
        jcenter()
        maven {
            setUrl("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }
    dependencies {
        classpath(Config.gradleAndroid)
        classpath(Config.gradleKotlin)
    }
}


allprojects {
    repositories {
        google()
        jcenter()
        maven {
            setUrl("https://dl.bintray.com/kotlin/kotlin-eap")
        }
        if ((group as String).isNotEmpty() && name != "lint" && name != "internal") {
            configAndroid()
        }
    }
}

rootProject.setBuildDir("../build")
subprojects {
    println(rootProject.buildDir)
    project.setBuildDir("${rootProject.buildDir}/${project.name}")
    println(project.buildDir)
    project.evaluationDependsOn(":app")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

fun Project.configAndroid() {
    val isApp = name.startsWith("app")
    if (isApp) {
        apply(plugin = Config.application)
    } else {
        apply(plugin = Config.library)
    }
    apply(plugin = Config.kotlinAndroid)
    apply(plugin = Config.kotlinAndroidExtensions)
    apply(plugin = Config.kotlinKapt)

    configure<com.android.build.gradle.BaseExtension> {
        compileSdkVersion(Config.compileSdkVersion)

        defaultConfig {
            minSdkVersion(Config.minSdkVersion)
            targetSdkVersion(Config.targetSdkVersion)
            testInstrumentationRunner = Config.instrumentationRunner
            if (isApp) {
                multiDexEnabled = true
            }
        }

        sourceSets["main"].java.srcDirs("src/main/kotlin")

        lintOptions {
            isCheckAllWarnings = true
            isWarningsAsErrors = true
            isAbortOnError = false
            disable("InvalidPackage")
        }

        compileOptions {
            sourceCompatibility = Config.sourceCompatibility
            targetCompatibility = Config.targetCompatibility
        }

        if (!isApp) {
            afterEvaluate {
                tasks.matching {
                    it.name.endsWith("BuildConfig")
                }.forEach { it ->
                    it.enabled = false
                }
            }
        }
    }
}