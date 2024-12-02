import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS
import org.gradle.kotlin.dsl.projects

plugins {
    alias(libs.plugins.dodam.multiplatform.feature)
    alias(libs.plugins.dodam.multiplatform.koin)
    alias(libs.plugins.dodam.multiplatform.coil)
}

kotlin {
    setIOS(
        name = "Notice",
        bundleId = "com.b1nd.dodam.notice",
    )

    sourceSets.commonMain.dependencies {
        implementation(projects.common)
        implementation(projects.ui)
        implementation(libs.kotlinx.datetime)
        implementation(projects.logging)

        implementation(libs.dodam.design.system.cmm)
        implementation(libs.multiplatform.compose.material)
    }
}

android {
    namespace = "com.b1nd.dodam.notice"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}