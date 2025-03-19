plugins {
    alias(libs.plugins.flush.android.library)
}

android {
    namespace = "com.example.flush.core.base"
}

dependencies {
    implementation(libs.androidx.activity.compose)
}