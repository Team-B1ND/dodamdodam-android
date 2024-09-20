import com.b1nd.dodam.dsl.androidLibrary
import com.b1nd.dodam.dsl.setIOS
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.dodam.multiplatform.feature)
    alias(libs.plugins.dodam.multiplatform.koin)
    alias(libs.plugins.dodam.multiplatform.kotlin.serialization)
}


kotlin{
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    setIOS(
        name = "NightStudy",
        bundleId = "com.b1nd.dodam.nightstudy"
    )

    sourceSets{
        commonMain.dependencies {
            implementation(projects.common)
            api(projects.data.nightStudy)
            api(projects.network.core)
            implementation(libs.dodam.design.system.cmm)
            implementation(projects.ui)
        }
    }
}


androidLibrary {
    namespace = "com.b1nd.dodam.nightstudy"
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


