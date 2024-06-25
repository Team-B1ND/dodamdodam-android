import com.b1nd.dodam.dsl.android

plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.android.hilt)
    alias(libs.plugins.dodam.koin)
}

android {
    android {
        namespace = "com.b1nd.dodam.data.nightstudy"

        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}

dependencies {
    api(projects.data.core)
    implementation(projects.network.nightStudy)
    implementation(projects.common)

    implementation(libs.kotlinx.collections.immutable)
}
