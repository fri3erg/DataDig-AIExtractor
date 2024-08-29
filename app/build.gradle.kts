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
    namespace = "com.example.tesifrigo"
    compileSdk = 34

    defaultConfig {
        ndk {
            // On Apple silicon, you can omit x86_64.
            abiFilters += listOf("arm64-v8a", "x86_64")
        ***REMOVED***
        applicationId = "com.example.tesifrigo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "EXPO_PUBLIC_SUPABASE_URL",
            localProperties.getProperty("EXPO_PUBLIC_SUPABASE_URL")
        )
        buildConfigField("String", "EXPO_PUBLIC_SUPABASE_ANON_KEY",
            localProperties.getProperty("EXPO_PUBLIC_SUPABASE_ANON_KEY")
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        ***REMOVED***
    ***REMOVED***

    buildTypes {
        debug {
***REMOVED***"String", "EXPO_PUBLIC_SUPABASE_URL",
***REMOVED***
***REMOVED***
***REMOVED***"String", "EXPO_PUBLIC_SUPABASE_ANON_KEY",
***REMOVED***
***REMOVED***
        ***REMOVED***

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
***REMOVED***
***REMOVED***"String", "EXPO_PUBLIC_SUPABASE_URL", "\"https://evxuxenxmtadutqpunue.supabase.co\"")
***REMOVED***"String", "EXPO_PUBLIC_SUPABASE_ANON_KEY", "\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImV2eHV4ZW54bXRhZHV0cXB1bnVlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjMzODg1MDYsImV4cCI6MjAzODk2NDUwNn0.o_kS_WFx5PsmuO9Jb51T_ytPjSqOZzicdQxPJXJpvFg\"")
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
                // "-r"` followed by a requirements filename, relative to the
                // project directory:



                install("pandas")
                install("pillow")



                //install("pydantic_core")
                //install("instructor == 0.3.0")
                install("-r", projectDir.absolutePath + "/src/main/python/requirements_kotlin.txt")
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***
        sourceSets {
        ***REMOVED***
    kapt{
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
    // For Android Gradle Plugin tasks

***REMOVED***
// For Android Gradle Plugin and Google Services Plugin (Java 11)



dependencies {
    implementation(libs.androidx.ui.v1610)
    implementation(libs.composetheme)
    implementation(libs.vision.common)
    implementation(libs.play.services.mlkit.text.recognition.common)
    implementation(libs.play.services.mlkit.text.recognition)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.android)
    runtimeOnly(libs.tess.two)
    implementation(libs.firebase.crashlytics) // Or latest version
    kapt(libs.hilt.android.compiler)
    implementation(libs.android.image.cropper) // or later version
    implementation (libs.androidx.material3.v100)
    implementation (libs.ui.tooling.preview)
    kapt(libs.hilt.android)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.work.runtime.ktx.v281)
    runtimeOnly(libs.kotlinx.metadata.jvm) // Use the latest version
    implementation(libs.androidx.foundation) // Or the latest version
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.security.crypto) // Or the latest version
    implementation (libs.fontawesomecompose)
    implementation(libs.coil.compose)
    runtimeOnly(libs.cronet.embedded)
    implementation(libs.androidx.camera.lifecycle)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    runtimeOnly (libs.camera.camera2)
    implementation (libs.androidx.camera.view.v130alpha06)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.library.base)
    implementation (libs.gson)

    implementation(platform("io.github.jan-tennert.supabase:bom:2.5.4"))
    implementation(libs.postgrest.kt)
    implementation(libs.supabase.gotrue.kt)
    implementation(libs.realtime.kt)
    implementation(libs.ktor.client.android)
    runtimeOnly(libs.ktor.client.serialization.jvm) // Or the latest version
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.ui.tooling.preview)

    androidTestImplementation("androidx.test:monitor:1.7.1")
    androidTestImplementation(libs.junit)
    implementation("androidx.activity:activity:1.9.1")
    implementation("androidx.annotation:annotation:1.8.1")
    implementation("androidx.compose.animation:animation-core:1.7.0-alpha08")
    implementation("androidx.compose.animation:animation:1.7.0-alpha08")
    implementation("androidx.compose.foundation:foundation-layout:1.7.0-alpha08")
    implementation("androidx.compose.material:material-icons-core:1.6.8")
    implementation("androidx.compose.runtime:runtime:1.7.0-alpha08")
    implementation("androidx.compose.ui:ui-geometry:1.7.0-alpha08")
    implementation("androidx.compose.ui:ui-text:1.7.0-alpha08")
    implementation("androidx.compose.ui:ui-unit:1.7.0-alpha08")
    implementation("androidx.core:core:1.13.1")
    implementation("androidx.fragment:fragment:1.6.2")
    implementation("androidx.lifecycle:lifecycle-common:2.8.4")
    implementation("androidx.lifecycle:lifecycle-process:2.8.4")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.4")
    implementation("androidx.navigation:navigation-common:2.8.0-alpha08")
    implementation("androidx.navigation:navigation-runtime:2.8.0-alpha08")
    implementation("androidx.savedstate:savedstate:1.2.1")
    implementation("com.google.dagger:dagger:2.51")
    implementation("com.google.dagger:hilt-core:2.51")
    implementation("com.google.guava:listenablefuture:1.0")
    implementation("io.ktor:ktor-http:2.3.12")
    implementation("io.realm.kotlin:cinterop:1.11.0")
    implementation("io.realm.kotlin:jni-swig-stub:1.11.0")
    implementation("javax.inject:javax.inject:1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.3")
    kapt("com.google.dagger:dagger-compiler:2.51")

***REMOVED***
kapt {
    correctErrorTypes = true
***REMOVED***