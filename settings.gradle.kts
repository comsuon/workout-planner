pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
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
rootProject.name = "Workout Planner"
include(":app")
include(":core:data")
include(":core:model")
include(":core:database")
include(":core:common")
include(":core:ui")
include(":feature:collections")
include(":feature:editor")
include(":core:domain")
