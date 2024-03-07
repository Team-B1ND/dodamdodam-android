@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.dodam.android.feature)
}

android {
    namespace = "com.b1nd.dodam.student.home"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.data.meal)
    implementation(projects.data.wakeupSong)
    implementation(projects.data.outing)
    implementation(projects.common)

    implementation(libs.coil.compose)
}
