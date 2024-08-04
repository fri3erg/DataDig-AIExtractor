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
    implementation(libs.androidx.ui.v1610)
    implementation(libs.composetheme)

    implementation(libs.material3) // Or the latest version
    implementation(libs.vision.common)
    implementation(libs.play.services.mlkit.text.recognition.common)
    implementation(libs.play.services.mlkit.text.recognition)
    implementation(libs.androidx.paging.common.android)
    implementation(libs.firebase.crashlytics.buildtools)
    val nav_version = "2.8.0-alpha08"
    implementation(libs.text.recognition) // Or latest version
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation(libs.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.androidx.room.ktx)
    implementation(libs.tess.two) // Or latest version
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    implementation (libs.androidx.ui.v133)
    implementation (libs.androidx.material3.v100)
    implementation (libs.ui.tooling.preview)
    implementation (libs.androidx.activity.compose.v161)
    kapt(libs.hilt.android)
    implementation(libs.androidx.hilt.work)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0") // Use the latest version
    implementation ("com.google.accompanist:accompanist-pager:0.31.2-alpha") // Replace with the latest version
    implementation(libs.google.accompanist.pager)
    implementation("androidx.compose.foundation:foundation:1.6.8") // Or the latest version

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.core.ktx.v1131) // Or the latest version
    implementation(libs.accompanist.permissions) // Replace with your desired version
    implementation(libs.camera.camera2.v133) // Or latest stable version
    implementation(libs.androidx.camera.core)
    implementation(libs.ui) // Or a more recent version
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8") // Or same version
    implementation(libs.androidx.security.crypto) // Or the latest version
    implementation(libs.androidx.localbroadcastmanager)
    implementation (libs.fontawesomecompose)
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
    implementation(libs.kotlinx.serialization.json)
    implementation("io.realm.kotlin:library-base:1.11.0")
    implementation ("com.google.code.gson:gson:2.10.1")





***REMOVED***
kapt {
    correctErrorTypes = true
***REMOVED***