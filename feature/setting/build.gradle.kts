plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.hilt)
    alias(libs.plugins.dodam.koin)
}

android {
    namespace = "com.b1nd.dodam.setting"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.dodam.design.system)
    implementation(projects.data.member)
    implementation(projects.common)
    implementation(projects.ui)
    implementation(libs.coil.compose)
}
