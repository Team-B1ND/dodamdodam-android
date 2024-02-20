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
    ":feature:meal",
    ":network:core",
    ":network:login",
    ":network:meal",
    ":data:login",
    ":model",
    ":common",
    ":keystore",
    ":datastore",
    ":data:register",
    ":data:meal",
    ":network:register",
    ":feature-student:main",
    ":feature-student:home",
    ":ui-test-main",
)