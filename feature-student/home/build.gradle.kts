@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
}

android {
    namespace = "com.b1nd.dodam.student.home"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.dodam.design.system)
    implementation(projects.data.meal)
    implementation(projects.data.wakeupSong)
    implementation(projects.data.outing)
    implementation(projects.common)
    implementation(projects.data.core)
    implementation(projects.data.nightStudy)
    implementation(projects.data.schedule)
    implementation(projects.data.banner)
    implementation(projects.ui)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.datetime)
}
