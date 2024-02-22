plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.android.hilt)
    alias(libs.plugins.dodam.kotlin.serialization)
}

android {
    namespace = "com.b1nd.dodam.datastore"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.keystore)
    implementation(libs.androidx.datastore.preference)
}
