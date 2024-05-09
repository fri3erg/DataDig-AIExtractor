plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.chaquo.python")

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
        defaultConfig{
            version = "3.11"
        ***REMOVED***
        sourceSets {
            getByName("main") {
                srcDir("src/main/python")
            ***REMOVED***
        ***REMOVED***




    ***REMOVED***
***REMOVED***

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("com.google.accompanist:accompanist-permissions:0.28.0") // Replace with your desired version
    implementation("androidx.camera:camera-camera2:1.3.3") // Or latest stable version
    implementation(libs.androidx.camera.core)
    implementation("androidx.compose.ui:ui:1.4.0") // Or a more recent version
    implementation("androidx.compose.runtime:runtime-livedata:1.4.0") // Or same version

    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")
    implementation ("com.github.Gurupreet:FontAwesomeCompose:1.0.0")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.cronet.embedded)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
***REMOVED***