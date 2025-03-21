plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
}

android {
    namespace = "com.b1nd.dodam.outing"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.ui)
    implementation(libs.dodam.design.system.cmm)
    implementation(projects.data.outing)
    implementation(libs.kotlinx.datetime)
}
