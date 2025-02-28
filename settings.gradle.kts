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
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
        maven("https://oss.sonatype.org/content/repositories/snapshots") {
            name = "SonatypeSnapshots"
            mavenContent { snapshotsOnly() }
        }
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
    ":feature-student:register",
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
    ":feature-student:nightstudy",
    ":feature-student:outing",
    ":ui",
    ":feature-student:all",
    ":data:member",
    ":network:member",
    ":feature-student:wakeup-song",
    ":feature-student:ask-out",
    ":feature-student:ask-nightstudy",
    ":feature-student:bus",
    ":data:bus",
    ":network:bus",
    ":feature:setting",
    ":network:point",
    ":data:point",
    ":feature-student:point",
    ":feature-student:ask-wakeup-song",
    ":logging",
    ":dodam-teacher-android",
    ":feature-teacher:register",
    ":feature-teacher:home",
    ":feature-teacher:point",
    ":feature-teacher:all",
    ":feature:notice",
    ":feature-teacher:notice-create",
    ":feature:group-detail",
    ":feature:group-waiting",
    ":feature:group-create",
    ":feature:group-add",
    ":data:division",
    ":network:division",
    ":data:notice",
    ":network:notice",
    ":feature-student:bus-apply",
    ":feature-teacher:bus-management",
)
include(":feature-teacher:nightstudy")
include(":feature-teacher:outing")
include(":feature-teacher:approve-outing")
include(":feature-teacher:approve-nightstudy")
include(":network:bundleid-info")
include(":data:bundleid-info")
include(":feature:edit-member-info")
include(":network:upload")
include(":data:upload")
include(":feature-parent")
include(":feature-parent:main")
include(":feature-parent:home")
include(":feature-parent:all")

include(":feature:group")
project(":feature:group").name = "feature-group-alias"
include(":feature-parent:children-manage")
