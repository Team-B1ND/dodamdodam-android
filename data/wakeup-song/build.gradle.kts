import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.kotlin)
    alias(libs.plugins.dodam.multiplatform.koin)
}

kotlin {
    setIOS("data.wakeupsong")

    sourceSets.commonMain.dependencies {
        api(projects.data.core)
        implementation(projects.common)
        implementation(projects.network.wakeupSong)

    }
}


android {
    namespace = "com.b1nd.dodam.data.wakeupsong"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}