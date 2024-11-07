plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
}

android {
    namespace = "com.b1nd.dodam.member"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.datastore)
    implementation(libs.dodam.design.system)
    implementation(libs.dodam.design.system.cmm)
    implementation(projects.data.member)
    implementation(projects.ui)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.datetime)
}
