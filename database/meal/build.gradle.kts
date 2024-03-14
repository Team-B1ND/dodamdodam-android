import com.b1nd.dodam.dsl.android

plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.android.hilt)
    alias(libs.plugins.dodam.android.database)
}

android {
    namespace = "com.b1nd.dodam.database.meal"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
