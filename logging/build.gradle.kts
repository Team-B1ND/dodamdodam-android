import com.b1nd.dodam.dsl.setIOS
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.dodam.multiplatform)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
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
