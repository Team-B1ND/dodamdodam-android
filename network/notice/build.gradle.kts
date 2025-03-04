import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.kotlin.serialization)
    alias(libs.plugins.dodam.multiplatform.koin)
}

kotlin {
    setIOS("network.notice")

    sourceSets.commonMain.dependencies {
        api(projects.network.core)
        implementation(projects.common)
        implementation(libs.kotlinx.collections.immutable)
    }
}


android {
    android {
        namespace = "com.b1nd.dodam.network.notice"

        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}
