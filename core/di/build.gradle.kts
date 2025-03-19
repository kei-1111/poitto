plugins {
    alias(libs.plugins.flush.android.library)
    alias(libs.plugins.flush.hilt)
}

android {
    namespace = "com.example.flush.core.di"
}

dependencies {
    implementation(projects.data.api)
    implementation(projects.data.repository)
    implementation(projects.core.repository)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    implementation(libs.play.services.auth)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
}