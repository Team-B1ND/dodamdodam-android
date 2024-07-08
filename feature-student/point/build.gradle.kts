plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.koin)
}

android {
    namespace = "com.b1nd.dodam.student.point"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.ui)
    implementation(libs.dodam.design.system)
    implementation(projects.data.point)
    implementation(libs.kotlinx.datetime)
}
