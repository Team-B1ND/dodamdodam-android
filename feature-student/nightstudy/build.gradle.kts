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
    implementation(libs.dodam.design.system)
    implementation(projects.ui)
    implementation(projects.data.nightStudy)
    implementation(libs.kotlinx.datetime)
}
