
plugins {
    id("java-library")
    alias(libs.plugins.kotlin.multiplatform)
//    alias(libs.plugins.dodam.kotlin)
}

kotlin {
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
