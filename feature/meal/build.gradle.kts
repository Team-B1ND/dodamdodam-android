import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS
import org.gradle.kotlin.dsl.projects

plugins {
    alias(libs.plugins.dodam.multiplatform.feature)
    alias(libs.plugins.dodam.multiplatform.koin)
}

kotlin {
    setIOS(
        name = "Meal",
        bundleId = "com.b1nd.dodam.meal",
    )

    sourceSets.commonMain.dependencies {
        implementation(projects.common)
        implementation(projects.datastore)
        implementation(projects.ui)
        implementation(projects.data.meal)
        implementation(libs.kotlinx.datetime)

        implementation(libs.dodam.design.system.cmm)
        implementation(libs.multiplatform.compose.material)
    }
}

android {
    namespace = "com.b1nd.dodam.meal"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}