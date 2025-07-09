import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.chaquo.python")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("io.realm.kotlin")
    kotlin("plugin.serialization") version "1.9.23"
    alias(libs.plugins.googleGmsGoogleServices)
    alias(libs.plugins.googleFirebaseCrashlytics)
    id("com.autonomousapps.dependency-analysis")
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}

android {
    namespace = "com.friberg.dataDig"
    compileSdk = 34

    defaultConfig {
        ndk {
            // On Apple silicon, you can omit x86_64.
            abiFilters += listOf("arm64-v8a", "x86_64")
        }
        applicationId = "com.friberg.dataDig"
        minSdk = 24
        targetSdk = 34
        versionCode = 6
        versionName = "1.4"

        buildConfigField(
            "String",
            "EXPO_PUBLIC_SUPABASE_URL",
            localProperties.getProperty("EXPO_PUBLIC_SUPABASE_URL")
                ?: error("EXPO_PUBLIC_SUPABASE_URL not found in local.properties for release build!")
        )
        buildConfigField(
            "String",
            "EXPO_PUBLIC_SUPABASE_ANON_KEY",
            localProperties.getProperty("EXPO_PUBLIC_SUPABASE_ANON_KEY")
                ?: error("EXPO_PUBLIC_SUPABASE_ANON_KEY not found in local.properties for release build!")
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(project.findProperty("MY_STORE_FILE") ?: "keystore.jks")
            storePassword = project.findProperty("MY_STORE_PASSWORD") as String?
            keyAlias = project.findProperty("MY_KEY_ALIAS") as String?
            keyPassword = project.findProperty("MY_KEY_PASSWORD") as String?
        }
    }

    buildTypes {
        debug {
            // your debug config here if needed
        }
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    flavorDimensions += "pyVersion"
    productFlavors {
        create("py311") { dimension = "pyVersion" }
    }

    chaquopy {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        productFlavors {
            getByName("py311") { version = "3.11" }
        }

        defaultConfig {
            version = "3.11"

            java {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
            pip {
                install("pandas")
                install("pillow")
                install("-r", projectDir.absolutePath + "/src/main/python/requirements_kotlin.txt")
            }
        }
    }

    sourceSets {}

    kapt {
        javacOptions {
            option("-source", "11")
            option("-target", "11")
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

dependencies {
    // Your full dependency list goes here (unchanged)
    // ...
}

kapt {
    correctErrorTypes = true
}
