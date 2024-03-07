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
    ":ui-test-main",
    ":designsystem",
    ":feature:onboarding",
    ":feature:login",
    ":network:core",
    ":network:login",
    ":network:meal",
    ":network:wakeup-song",
    ":network:outing",
    ":data:core",
    ":data:meal",
    ":data:login",
    ":data:wakeup-song",
    ":data:outing",
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
