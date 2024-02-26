import com.b1nd.dodam.dsl.android

plugins {
    alias(libs.plugins.dodam.kotlin)
    alias(libs.plugins.dodam.kotlin.serialization)
    alias(libs.plugins.dodam.hilt)
}

android {
    namespace = "com.b1nd.dodam.network.register"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    api(projects.network.core)
    implementation(projects.common)

    implementation(libs.kotlinx.collections.immutable)

    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}