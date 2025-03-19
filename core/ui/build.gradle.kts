plugins {
    alias(libs.plugins.flush.android.library.compose)
}

android {
    namespace = "com.example.flush.core.ui"
}

dependencies {
    implementation(libs.sceneview)
}