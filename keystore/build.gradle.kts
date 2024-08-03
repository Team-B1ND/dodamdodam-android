import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.koin)
}

kotlin {
    setIOS("keystore")

    sourceSets {
        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(projects.common)
        }
    }
}

android {
    namespace = "com.b1nd.dodam.keystore"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
