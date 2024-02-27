plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.hilt)
}

android {
    namespace = "com.b1nd.dodam.register"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.data.register)
    implementation(libs.bottomsheetdialog.compose)
    implementation(projects.common)
}
