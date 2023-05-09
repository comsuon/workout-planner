// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val compose_version by extra("1.0.1")
    val nav_version by extra("2.3.5")
    val room_version by extra("2.3.0")
    val hilt_version by extra("2.38.1")
    val kotlin_version by extra("1.8.21")


    repositories {
        google()
        mavenCentral()
    }

    //    dependencies {
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
//        classpath("com.android.tools.build:gradle")
//    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}