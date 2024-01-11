plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.android.compose)
}

android {
    namespace = "com.b1nd.dodam.designsystem"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
