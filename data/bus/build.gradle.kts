import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.kotlin)
    alias(libs.plugins.dodam.multiplatform.koin)
}

kotlin {
    setIOS("data.bus")

    sourceSets.commonMain.dependencies {
        implementation(projects.common)
        implementation(projects.network.bus)
    }
}


android {
    namespace = "com.b1nd.dodam.data.bus"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
