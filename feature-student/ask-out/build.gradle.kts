plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
}

android {
    namespace = "com.b1nd.dodam.ask_out"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.ui)
    implementation(projects.data.outing)
    implementation(libs.dodam.design.system.cmm)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bottomsheetdialog.compose)
}
