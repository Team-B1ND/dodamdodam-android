import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.kotlin.serialization)
    alias(libs.plugins.dodam.multiplatform.koin)
}

kotlin {
    sourceSets {
        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(projects.datastore)
            implementation(projects.common)
            api(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.auth)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.serialization.kotlinx.json)
            api(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
    setIOS("network.core")
}

android {
    namespace = "com.b1nd.dodam.network.core"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
