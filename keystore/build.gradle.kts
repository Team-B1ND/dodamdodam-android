import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.koin)
    alias(libs.plugins.swift.klib)
}

kotlin {
    setIOS("keystore") {
        compilations.getByName("main") {
            cinterops.create("Keystore")
        }
    }

    sourceSets {
        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(projects.common)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.b1nd.dodam.keystore"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

swiftklib {
    create("Keystore") {
        path = file("native/Keystore")
        packageName("com.b1nd.dodam.ios.keystore")
    }
}