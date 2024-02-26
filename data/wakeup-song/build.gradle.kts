import com.b1nd.dodam.dsl.android

plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.android.hilt)
}

android {
    namespace = "com.b1nd.dodam.data.wakeupsong"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.model)
    implementation(projects.network.wakeupSong)
    implementation(projects.common)

    implementation(libs.kotlinx.collections.immutable)
}