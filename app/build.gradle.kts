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
***REMOVED***

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    ***REMOVED***
***REMOVED***

android {
    namespace = "com.friberg.dataDig"
    compileSdk = 34

    defaultConfig {
        ndk {
            // On Apple silicon, you can omit x86_64.
            abiFilters += listOf("arm64-v8a", "x86_64")
        ***REMOVED***
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
        ***REMOVED***
    ***REMOVED***

    signingConfigs {
        create("release") {
            storeFile = file(project.findProperty("MY_STORE_FILE") ?: "keystore.jks")
            storePassword = project.findProperty("MY_STORE_PASSWORD") as String?
            keyAlias = project.findProperty("MY_KEY_ALIAS") as String?
            keyPassword = project.findProperty("MY_KEY_PASSWORD") as String?
        ***REMOVED***
    ***REMOVED***

    buildTypes {
        debug {
            // your debug config here if needed
        ***REMOVED***
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        ***REMOVED***
    ***REMOVED***

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    ***REMOVED***

    kotlinOptions {
        jvmTarget = "11"
    ***REMOVED***

    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    ***REMOVED***

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    ***REMOVED***

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1***REMOVED***"
        ***REMOVED***
    ***REMOVED***

    flavorDimensions += "pyVersion"
    productFlavors {
        create("py311") { dimension = "pyVersion" ***REMOVED***
    ***REMOVED***

    chaquopy {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        ***REMOVED***
        productFlavors {
            getByName("py311") { version = "3.11" ***REMOVED***
        ***REMOVED***

        defaultConfig {
            version = "3.11"

            java {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            ***REMOVED***
            pip {
                install("pandas")
                install("pillow")
                install("-r", projectDir.absolutePath + "/src/main/python/requirements_kotlin.txt")
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    sourceSets {***REMOVED***

    kapt {
        javacOptions {
            option("-source", "11")
            option("-target", "11")
        ***REMOVED***
    ***REMOVED***

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
        ***REMOVED***
    ***REMOVED***
***REMOVED***

dependencies {
    // Your full dependency list goes here (unchanged)
    // ...
***REMOVED***

kapt {
    correctErrorTypes = true
***REMOVED***
