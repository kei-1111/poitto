plugins {
    alias(libs.plugins.flush.android.library)
    alias(libs.plugins.flush.kotlin.serialization)
}

android {
    namespace = "com.example.flush.data.api"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.retrofit2)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
}