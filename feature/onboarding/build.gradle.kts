plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.kotlin.serialization)
}

android {
    namespace = "com.b1nd.dodam.onboarding"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.dodam.design.system)
    implementation(libs.bottomsheetdialog.compose)
}
