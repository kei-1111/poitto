plugins {
    alias(libs.plugins.flush.android.library.compose)
}

android {
    namespace = "com.example.flush.core.designsystem"
}

dependencies {
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material3)
    implementation(libs.coil.compose)
    implementation(libs.lottie.compose)
}