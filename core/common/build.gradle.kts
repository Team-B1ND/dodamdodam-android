plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dodam.android.compose)
}

android {
    namespace = "com.b1nd.dodam.common"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {

}
