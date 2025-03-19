plugins {
    alias(libs.plugins.flush.android.library)
}

android {
    namespace = "com.example.flush.core.repository"
}

dependencies {
    implementation(projects.core.model)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.kotlin.result)
}