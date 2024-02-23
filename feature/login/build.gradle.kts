plugins {
    alias(libs.plugins.dodam.android.feature)
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
    implementation(projects.designsystem)
    implementation(libs.androidx.datastore.preference)
}
