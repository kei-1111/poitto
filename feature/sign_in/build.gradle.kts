plugins {
    alias(libs.plugins.flush.android.feature)
}

android {
    namespace = "com.example.flush.feature.sign_in"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.utils)

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material3)
    implementation(libs.kotlin.result)
}