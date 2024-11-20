plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
}

android {
    namespace = "com.b1nd.dodam.wakeup_song"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.ui)
    implementation(libs.coil.compose)
    implementation(libs.dodam.design.system.cmm)
    implementation(projects.data.wakeupSong)
    implementation(libs.kotlinx.datetime)
}
