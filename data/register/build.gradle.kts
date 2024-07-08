import com.b1nd.dodam.dsl.android

plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.android.hilt)
    alias(libs.plugins.dodam.koin)
}

android {
    namespace = "com.b1nd.dodam.data.register"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.network.register)
}
