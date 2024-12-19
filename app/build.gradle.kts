plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

//    detekt
    alias(libs.plugins.detekt)

//    KSP
    alias(libs.plugins.ksp)

//    Hilt
    alias(libs.plugins.hilt)

//    Serialization
    alias(libs.plugins.serialization)

//    Google Services
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.flush"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.flush"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":core:designsystem"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))

    implementation(project(":feature:auth_selection"))
    implementation(project(":feature:post"))
    implementation(project(":feature:search"))
    implementation(project(":feature:sign_in"))
    implementation(project(":feature:sign_up"))
    implementation(project(":feature:user_settings"))

//    Splash Screen
    implementation(libs.androidx.core.splashscreen)

//    detekt
    detektPlugins(libs.detekt.compose)
    detektPlugins(libs.detekt.formatting)

//    Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

//    Serialization
    implementation(libs.kotlinx.serialization.json)

//    Navigation
    implementation(libs.androidx.navigation.compose)

//    Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        config.setFrom("$rootDir/config/detekt/detekt.yml")
        buildUponDefaultConfig = true

        source.setFrom(files("src/main/java"))
        autoCorrect = true
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
        jvmTarget = "17"
    }
}
