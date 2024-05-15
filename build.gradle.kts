// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.chaquo.python") version "15.0.1" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
    id("io.realm.kotlin") version "1.11.0" apply false

***REMOVED***
buildscript {
    dependencies {
        // ... other dependencies
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
    ***REMOVED***
***REMOVED***