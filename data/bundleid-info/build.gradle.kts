import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.kotlin)
    alias(libs.plugins.dodam.multiplatform.koin)
}

kotlin {
    setIOS("data.bundleidinfo")

    sourceSets.commonMain.dependencies {
        api(projects.data.core)
        implementation(projects.common)
        implementation(projects.network.bundleidInfo)


    }
}


android {
    android {
        namespace = "com.b1nd.dodam.data.bundleidinfo"

        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}
