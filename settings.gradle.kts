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
    ":feature:register",
    ":network:core",
    ":network:login",
    ":network:meal",
    ":data:login",
    ":model",
    ":common",
    ":keystore",
    ":datastore"
)
