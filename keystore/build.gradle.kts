
plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.koin)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.b1nd.dodam.keystore"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(projects.common)
    implementation(libs.androidx.core.ktx)
}