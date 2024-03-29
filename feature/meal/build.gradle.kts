import org.gradle.kotlin.dsl.projects

plugins {
    alias(libs.plugins.dodam.android.feature)
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
    implementation(projects.designsystem)
    implementation(projects.data.meal)
    implementation(libs.material)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.datastore.preference)
}