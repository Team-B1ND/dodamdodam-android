import com.b1nd.dodam.dsl.android

plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.android.kotlin)
}

android {
    android {
        namespace = "com.b1nd.dodam.data.core"

        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}

dependencies {
    implementation(projects.network.core)
    implementation(projects.common)

    implementation(libs.kotlinx.collections.immutable)
}
