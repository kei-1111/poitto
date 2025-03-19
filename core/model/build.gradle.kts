plugins {
    alias(libs.plugins.flush.android.library.compose)
    alias(libs.plugins.flush.kotlin.serialization)
}

android {
    namespace = "com.example.flush.core.model"
}
