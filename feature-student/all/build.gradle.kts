plugins {
    alias(libs.plugins.dodam.android.feature)
}

android {
    namespace = "com.b1nd.dodam.nightstudy"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.datastore)
    implementation(projects.designsystem)
    implementation(projects.data.my)
    implementation(libs.kotlinx.datetime)
}
