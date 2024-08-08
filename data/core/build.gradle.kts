import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.kotlin)
}

kotlin {
    setIOS("data.core")

    sourceSets.commonMain.dependencies {
        implementation(projects.common)
        implementation(projects.network.core)

    }
}


android {
    android {
        namespace = "com.b1nd.dodam.data.core"

        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}