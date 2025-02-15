@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
}

android {
    namespace = "com.b1nd.dodam.parent.home"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.dodam.design.system.cmm)
    implementation(projects.data.meal)
    implementation(projects.common)
    implementation(projects.data.core)
    implementation(projects.data.schedule)
    implementation(projects.data.banner)
    implementation(projects.featureStudent.home)
    implementation(projects.ui)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.datetime)
}
