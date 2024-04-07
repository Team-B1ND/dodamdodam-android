plugins {
    alias(libs.plugins.dodam.android.feature)
}

android {
    namespace = "com.b1nd.dodam.ask_wakeup_song"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.ui)
    implementation(libs.coil.compose)
    implementation(libs.dodam.design.system)
    implementation(projects.data.wakeupSong)
    implementation(libs.kotlinx.datetime)
}
