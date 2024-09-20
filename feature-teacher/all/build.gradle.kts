import com.b1nd.dodam.dsl.androidLibrary
import com.b1nd.dodam.dsl.debugImplementation
import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.implementationPlatform
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.dodam.multiplatform.feature)
    alias(libs.plugins.dodam.multiplatform.koin)
    alias(libs.plugins.dodam.multiplatform.coil)
}

kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    setIOS(
        name = "All",
        bundleId = "com.b1nd.dodam.all"
    )

    sourceSets {
        commonMain.dependencies {
            implementation(libs.dodam.design.system.cmm)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(projects.common)
            implementation(projects.ui)
            implementation(projects.logging)
            implementation(projects.data.member)
        }
    }
}

androidLibrary {
    namespace = "com.b1nd.dodam.home"
    compileSdk = libs.versions.compileSdk.get().toInt()

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    composeCompiler {
        enableStrongSkippingMode = true

        reportsDestination = layout.buildDirectory.dir("compose_compiler")
        stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
    }
}