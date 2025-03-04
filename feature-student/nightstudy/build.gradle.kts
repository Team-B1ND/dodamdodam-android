plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
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
    implementation(libs.dodam.design.system.cmm)
    implementation(projects.ui)
    implementation(projects.data.nightStudy)
    implementation(libs.kotlinx.datetime)
}
