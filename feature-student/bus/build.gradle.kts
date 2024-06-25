plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.koin)
}

android {
    namespace = "com.b1nd.dodam.bus"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(libs.dodam.design.system)
    implementation(projects.ui)
    implementation(projects.data.bus)
    implementation(libs.kotlinx.datetime)
}
