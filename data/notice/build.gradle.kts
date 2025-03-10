import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.kotlin)
    alias(libs.plugins.dodam.multiplatform.koin)
    alias(libs.plugins.dodam.multiplatform.kotlin.serialization)
}

kotlin {
    setIOS("data.notice")

    sourceSets.commonMain.dependencies {
        api(projects.data.core)
        implementation(projects.common)
        implementation(projects.network.notice)

    }
}


android {
    android {
        namespace = "com.b1nd.dodam.data.notice"

        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}
