plugins {
    alias(libs.plugins.flush.android.library.compose)
}

android {
    namespace = "com.example.flush.core.utils"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.sceneview)
}