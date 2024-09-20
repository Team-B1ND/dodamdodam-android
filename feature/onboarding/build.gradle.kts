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
    alias(libs.plugins.dodam.multiplatform.kotlin.serialization)
}

kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    setIOS(
        name = "Onboarding",
        bundleId = "com.b1nd.dodam.onboarding"
    )

    sourceSets {
        commonMain.dependencies {
            implementation(libs.dodam.design.system.cmm)
        }
    }
}

androidLibrary {
    namespace = "com.b1nd.dodam.onboarding"
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

dependencies {
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}