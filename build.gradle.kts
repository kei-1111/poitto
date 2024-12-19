// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

//    detekt
    alias(libs.plugins.detekt) apply false

//    KSP
    alias(libs.plugins.ksp) apply false

//    Hilt
    alias(libs.plugins.hilt) apply false

//    Serialization
    alias(libs.plugins.serialization) apply false

//    Google Services
    alias(libs.plugins.google.services) apply false

//    Secrets Gradle Plugin
    alias(libs.plugins.secrets.gradle.plugin) apply false
}
