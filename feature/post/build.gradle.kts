plugins {
    alias(libs.plugins.flush.android.feature)
}

android {
    namespace = "com.example.flush.feature.post"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.ui)
    implementation(projects.core.utils)

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material3)
    implementation(libs.kotlin.result)
    implementation(libs.sceneview)
}