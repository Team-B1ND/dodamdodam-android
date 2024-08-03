import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }
    
    setIOS("logging")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.stately.concurrency)
        }
    }
}

android {
    namespace = "com.b1nd.dodam.logging"
}
