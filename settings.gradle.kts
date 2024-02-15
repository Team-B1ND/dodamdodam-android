enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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

rootProject.name = "dodamdodam-android"
include(
    ":dodam-student",
    ":designsystem",
    ":feature:onboarding",
    ":feature:login",
    ":network:core",
    ":network:meal",
    ":common",
)
include(":network:login")
include(":data:login")
include(":model")
