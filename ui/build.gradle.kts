import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform.feature)
}

kotlin {
    setIOS(
        name = "ui",
        bundleId = "com.b1nd.dodam.ui"
    )
}

android {
    namespace = "com.b1nd.dodam.ui"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    api(libs.dodam.design.system)
}
