plugins {
    alias(libs.plugins.flush.android.library)
    alias(libs.plugins.flush.hilt)
}

android {
    namespace = "com.example.flush.core.domain"
}

dependencies {
    implementation(projects.core.di)
    implementation(projects.core.repository)
    implementation(projects.core.model)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.kotlin.result)
}