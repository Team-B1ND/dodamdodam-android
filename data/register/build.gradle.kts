import com.b1nd.dodam.dsl.android

plugins {
    alias(libs.plugins.dodam.kotlin)
    alias(libs.plugins.dodam.hilt)
}

android {
    android {
        namespace = "com.b1nd.dodam.data.register"

        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.network.register)
}
