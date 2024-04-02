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
        maven("https://jitpack.io")
    }
}

rootProject.name = "dodamdodam-android"
include(
    ":dodam-student",
    ":ui-test-main",
    ":designsystem",
    ":feature:onboarding",
    ":feature:login",
    ":feature:meal",
    ":feature:register",
    ":feature-student:main",
    ":feature-student:home",
    ":network:core",
    ":network:login",
    ":network:meal",
    ":network:wakeup-song",
    ":network:outing",
    ":network:night-study",
    ":network:register",
    ":network:schedule",
    ":network:banner",
    ":data:core",
    ":data:meal",
    ":data:login",
    ":data:wakeup-song",
    ":data:outing",
    ":data:register",
    ":data:meal",
    ":data:night-study",
    ":data:schedule",
    ":data:banner",
    ":common",
    ":keystore",
    ":datastore",
    ":ui-test-main",
    ":feature-student:nightstudy",
    ":feature-student:outing",
    ":ui",
    ":feature-student:all",
    ":data:member",
    ":network:member",
    ":feature-student:wakeup-song",
    ":feature-student:ask-out",
    ":feature-student:ask-nightstudy",
<<<<<<< HEAD
    ":feature-student:bus",
    ":data:bus",
    ":network:bus",
=======
    ":feature:setting",
>>>>>>> e7a0ae5c2d09e0ff3c726c4f2231c022948cdff9
)
