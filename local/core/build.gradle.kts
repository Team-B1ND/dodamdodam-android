import com.b1nd.dodam.dsl.android

plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.kotlin.serialization)
    alias(libs.plugins.dodam.android.hilt)
    alias(libs.plugins.realm)
}

android {
    namespace = "com.b1nd.dodam.local.core"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    api(libs.kotlinx.datetime)

    implementation(libs.realm.kotlin.base)
}
