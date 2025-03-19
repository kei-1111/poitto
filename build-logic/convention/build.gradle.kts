import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.example.flush.build_logic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradle)
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.detekt.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.flush.android.application.get().pluginId
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidFeature") {
            id = libs.plugins.flush.android.feature.get().pluginId
            implementationClass = "AndroidFeaturePlugin"
        }
        register("androidLibraryCompose") {
            id = libs.plugins.flush.android.library.compose.get().pluginId
            implementationClass = "AndroidLibraryComposePlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.flush.android.library.asProvider().get().pluginId
            implementationClass = "AndroidLibraryPlugin"
        }
        register("detekt") {
            id = libs.plugins.flush.detekt.get().pluginId
            implementationClass = "DetektPlugin"
        }
        register("hilt") {
            id = libs.plugins.flush.hilt.get().pluginId
            implementationClass = "HiltPlugin"
        }
        register("kotlinSerialization") {
            id = libs.plugins.flush.kotlin.serialization.get().pluginId
            implementationClass = "KotlinSerializationPlugin"
        }
    }
}

