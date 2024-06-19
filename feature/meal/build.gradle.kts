import org.gradle.kotlin.dsl.projects

plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.koin)
}

android {
    namespace = "com.b1nd.dodam.meal"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.datastore)
    implementation(libs.dodam.design.system)
    implementation(projects.ui)
    implementation(projects.data.meal)
    implementation(libs.material)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.datastore.preference)
}