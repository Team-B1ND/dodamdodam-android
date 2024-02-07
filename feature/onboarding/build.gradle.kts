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
    implementation(projects.designsystem)
    implementation(libs.bottomsheetdialog.compose)
}
