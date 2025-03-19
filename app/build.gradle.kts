plugins {
    alias(libs.plugins.flush.android.application)
    alias(libs.plugins.flush.hilt)
    alias(libs.plugins.flush.kotlin.serialization)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.flush"

    defaultConfig {
        applicationId = "com.example.flush"
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "WEB_CLIENT_ID", "\"${System.getenv("WEB_CLIENT_ID") ?: ""}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures.buildConfig = true
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(projects.feature.authSelection)
    implementation(projects.feature.post)
    implementation(projects.feature.search)
    implementation(projects.feature.signIn)
    implementation(projects.feature.signUp)
    implementation(projects.feature.userSettings)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.hilt.navigation.compose)
}
