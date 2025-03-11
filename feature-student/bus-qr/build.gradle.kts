plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
}

android {
    namespace = "com.b1nd.dodam.busqr"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
}

dependencies {
    implementation(projects.common)
    implementation(libs.dodam.design.system.cmm)
    implementation(projects.ui)
    implementation(projects.data.bus)
    implementation(libs.kotlinx.datetime)
    implementation(libs.qr.kit)
}
