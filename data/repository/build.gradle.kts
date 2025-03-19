plugins {
    alias(libs.plugins.flush.android.library)
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    namespace = "com.example.flush.data.repository"
    buildFeatures.buildConfig = true
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.repository)
    implementation(projects.core.utils)
    implementation(projects.data.api)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.kotlin.result)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.play.services.auth)
}