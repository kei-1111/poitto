plugins {
    alias(libs.plugins.flush.android.feature)
}

android {
    namespace = "com.example.flush.feature.auth_selection"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.model)

    implementation(libs.androidx.material3)
}