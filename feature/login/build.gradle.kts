plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.koin)
}

android {
    namespace = "com.b1nd.dodam.login"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.datastore)
    implementation(projects.data.login)
    implementation(projects.ui)
    implementation(libs.dodam.design.system)
    implementation(libs.androidx.datastore.preference)
}
