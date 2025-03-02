plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
}

android {
    namespace = "com.b1nd.dodam.register"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.dodam.design.system.cmm)
    implementation(libs.dodam.design.system)
    implementation(projects.data.register)
    implementation(libs.bottomsheetdialog.compose)
    implementation(projects.common)
    implementation(projects.ui)
}
