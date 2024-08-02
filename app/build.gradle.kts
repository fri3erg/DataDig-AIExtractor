plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.chaquo.python")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("io.realm.kotlin")
    kotlin("plugin.serialization") version "1.9.23"


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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        ***REMOVED***
    ***REMOVED***

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
***REMOVED***
        ***REMOVED***
    ***REMOVED***
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    ***REMOVED***

    kotlinOptions {
        jvmTarget = "1.8"
    ***REMOVED***
    buildFeatures {
        compose = true
        viewBinding = true
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
        productFlavors {
            getByName("py311") { version = "3.11" ***REMOVED***
        ***REMOVED***
        defaultConfig {
            version = "3.11"
            pip {
                // "-r"` followed by a requirements filename, relative to the
                // project directory:
                install("numpy")
                install("pandas")
                install("greenlet")
                install("multidict")
                //install("opencv-python")
                install("regex")
                install("pillow")
                install("-r", projectDir.absolutePath + "/src/main/python/requirements_kotlin.txt")
                extractPackages("tesseract")
            ***REMOVED***
        ***REMOVED***

    ***REMOVED***
        sourceSets {
        ***REMOVED***
    ***REMOVED***

dependencies {
    implementation(libs.androidx.material3.android)
    implementation(libs.vision.common)
    implementation(libs.play.services.mlkit.text.recognition.common)
    implementation(libs.play.services.mlkit.text.recognition)
    implementation(libs.androidx.paging.common.android)
    implementation(libs.firebase.crashlytics.buildtools)
    val nav_version = "2.8.0-alpha08"
    implementation("com.google.mlkit:text-recognition:16.0.0") // Or latest version
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("com.google.dagger:hilt-android:2.51")
    implementation(libs.androidx.room.ktx)
    implementation(libs.tess.two) // Or latest version
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    kapt("com.google.dagger:hilt-android:2.51")
    implementation("androidx.hilt:hilt-work:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0") // Use the latest version
    implementation ("com.google.accompanist:accompanist-pager:0.31.2-alpha") // Replace with the latest version
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("androidx.compose.foundation:foundation:1.4.3") // Or the latest version
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation("androidx.core:core-ktx:1.12.0") // Or the latest version
    implementation("com.google.accompanist:accompanist-permissions:0.28.0") // Replace with your desired version
    implementation("androidx.camera:camera-camera2:1.3.3") // Or latest stable version
    implementation(libs.androidx.camera.core)
    implementation("androidx.compose.ui:ui:1.4.0") // Or a more recent version
    implementation("androidx.compose.runtime:runtime-livedata:1.4.0") // Or same version
    implementation("androidx.security:security-crypto:1.1.0-alpha03") // Or the latest version
    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")
    implementation ("com.github.Gurupreet:FontAwesomeCompose:1.0.0")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.cronet.embedded)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.room.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.androidx.camera.core.v130alpha06)
    implementation (libs.camera.camera2)
    implementation (libs.androidx.camera.lifecycle.v130alpha06)
    implementation (libs.androidx.camera.view.v130alpha06)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("io.realm.kotlin:library-base:1.11.0")
    implementation ("com.google.code.gson:gson:2.8.6")





***REMOVED***
kapt {
    correctErrorTypes = true
***REMOVED***