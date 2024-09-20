import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.kotlin.serialization)
    alias(libs.plugins.dodam.multiplatform.koin)
}

kotlin {
    setIOS("network.point")

    sourceSets.commonMain.dependencies {
        api(projects.network.core)
        implementation(projects.common)
        implementation(libs.kotlinx.collections.immutable)
    }

    sourceSets.commonTest.dependencies {
        implementation(libs.kotlin.test)
        implementation(libs.ktor.client.mock)
        implementation(libs.kotlinx.coroutines.test)
    }
}

android {
    namespace = "com.b1nd.dodam.network.point"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}