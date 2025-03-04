import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.kotlin)
    alias(libs.plugins.dodam.multiplatform.koin)
}

kotlin {
    setIOS("data.meal")

    sourceSets.commonMain.dependencies {
        implementation(projects.common)
        implementation(projects.network.meal)

    }
}
android {
    android {
        namespace = "com.b1nd.dodam.data.meal"

        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}
