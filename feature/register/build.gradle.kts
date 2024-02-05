plugins {
    alias(libs.plugins.dodam.android.feature)
}

android {
    namespace = "com.b1nd.dodam.register"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(libs.bottomsheetdialog.compose)
    implementation(projects.core.common)
}