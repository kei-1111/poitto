plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

//    Google Services
    alias(libs.plugins.google.services)

//    Secrets Gradle Plugin
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    namespace = "com.example.flush.data.repository"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.data.api)

    implementation(projects.core.model)
    implementation(projects.core.repository)
    implementation(projects.core.utils)

//    Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)

//    Coroutines
    implementation(libs.kotlinx.coroutines.play.services)

//    Google Auth
    implementation(libs.play.services.auth)

//    kotlin-result
    implementation(libs.kotlin.result)
}