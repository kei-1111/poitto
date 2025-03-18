import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

//    detekt
    alias(libs.plugins.detekt)

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

//    flush
    alias(libs.plugins.flush.android.application) apply false
    alias(libs.plugins.flush.android.feature) apply false
    alias(libs.plugins.flush.android.library.compose) apply false
    alias(libs.plugins.flush.android.library) apply false
    alias(libs.plugins.flush.detekt) apply false
    alias(libs.plugins.flush.hilt) apply false
    alias(libs.plugins.flush.kotlin.serialization) apply false
}

val detektFormatting = libs.detekt.formatting
val detektCompose = libs.detekt.compose

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    dependencies {
        detektPlugins(detektFormatting)
        detektPlugins(detektCompose)
    }

    detekt {
        config.setFrom("$rootDir/config/detekt/detekt.yml")
        buildUponDefaultConfig = true

        source.setFrom(files("src/main/java"))
        autoCorrect = true
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = "17"
    }
}
