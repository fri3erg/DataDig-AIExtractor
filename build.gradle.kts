// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.chaquo.python") version "15.0.1" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
    id("io.realm.kotlin") version "1.11.0" apply false
    alias(libs.plugins.googleGmsGoogleServices) apply false
    alias(libs.plugins.googleFirebaseCrashlytics) apply false
    id("com.autonomousapps.dependency-analysis") version "1.31.0"


***REMOVED***
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") ***REMOVED***

    ***REMOVED***
    dependencies {
        // ... other dependencies
        classpath(libs.hilt.android.gradle.plugin)

    ***REMOVED***
***REMOVED***