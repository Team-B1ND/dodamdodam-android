import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform.feature)
    alias(libs.plugins.dodam.multiplatform.koin)
    alias(libs.plugins.dodam.multiplatform.coil)
}

kotlin {

    setIOS(
        name = "setting",
        bundleId = "com.b1nd.dodam.setting"
    )

    sourceSets.commonMain.dependencies {
        implementation(libs.dodam.design.system.cmm)
        implementation(projects.data.member)
        implementation(projects.common)
        implementation(projects.ui)
        implementation(projects.logging)
        implementation(projects.datastore)
    }
}

android {
    namespace = "com.b1nd.dodam.setting"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}