plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
}

android {
    namespace = "com.b1nd.dodam.ask_nightstudy"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.ui)
    implementation(projects.data.core)
    implementation(projects.data.nightStudy)
    implementation(libs.dodam.design.system)
    implementation(libs.kotlinx.datetime)
    implementation(libs.wheel.picker.compose)
    implementation(libs.bottomsheetdialog.compose)
}
