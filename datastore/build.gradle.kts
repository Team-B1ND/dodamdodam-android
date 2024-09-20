import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.kotlin.serialization)
    alias(libs.plugins.dodam.multiplatform.koin)
}

kotlin {
    setIOS("datastore")
    sourceSets {
        androidMain.dependencies {
            implementation(projects.keystore)
        }
        commonMain.dependencies {
            implementation(projects.common)
            implementation(libs.androidx.datastore.preferences.core)
        }
    }
}


android {
    namespace = "com.b1nd.dodam.datastore"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

