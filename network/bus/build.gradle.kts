import com.b1nd.dodam.dsl.android

plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.kotlin.serialization)
    alias(libs.plugins.dodam.koin)
}

android {
    namespace = "com.b1nd.dodam.network.bus"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    api(projects.network.core)
    implementation(projects.common)
    implementation(libs.kotlinx.collections.immutable)
}