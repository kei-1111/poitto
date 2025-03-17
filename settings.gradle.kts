pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "flush"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

include(":data:api")
include(":data:repository")

include(":core:repository")
include(":core:model")
include(":core:di")
include(":core:domain")
include(":core:designsystem")
include(":core:base")
include(":core:ui")
include(":core:utils")

include(":feature:auth_selection")
include(":feature:post")
include(":feature:search")
include(":feature:sign_in")
include(":feature:sign_up")
include(":feature:user_settings")
