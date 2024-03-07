import com.b1nd.dodam.dsl.android

plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.android.hilt)
}

android {
    android {
        namespace = "com.b1nd.dodam.data.outing"

        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}

dependencies {
    implementation(projects.data.core)
    implementation(projects.network.outing)
    implementation(projects.common)

    implementation(libs.kotlinx.collections.immutable)
}
