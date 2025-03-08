import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS
import org.gradle.kotlin.dsl.projects

plugins {
    alias(libs.plugins.dodam.multiplatform.feature)
    alias(libs.plugins.dodam.multiplatform.koin)
    alias(libs.plugins.dodam.multiplatform.coil)
    alias(libs.plugins.dodam.multiplatform.kotlin.serialization)
}

kotlin {
    setIOS(
        name = "NoticeViewer",
        bundleId = "com.b1nd.dodam.notice_viewer",
    )

    sourceSets.commonMain.dependencies {
        implementation(projects.common)
        implementation(projects.ui)
        implementation(projects.logging)
        implementation(projects.data.notice)
        implementation(libs.kotlinx.datetime)
        implementation(libs.dodam.design.system.cmm)
        implementation(libs.kotlinx.serialization.core)
    }
}

android {
    namespace = "com.b1nd.dodam.notice_viewer"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}