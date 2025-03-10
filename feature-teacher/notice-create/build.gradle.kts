import com.b1nd.dodam.dsl.androidLibrary
import com.b1nd.dodam.dsl.setIOS
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.dodam.multiplatform.feature)
    alias(libs.plugins.dodam.multiplatform.koin)
    alias(libs.plugins.dodam.multiplatform.kotlin.serialization)
    alias(libs.plugins.dodam.multiplatform.coil)
}


kotlin{
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    setIOS(
        name = "NoticeCreate",
        bundleId = "com.b1nd.dodam.noticecreate"
    )

    sourceSets{
        commonMain.dependencies {
            implementation(projects.logging)
            implementation(projects.common)
            implementation(libs.dodam.design.system.cmm)
            implementation(projects.ui)
            implementation(projects.data.upload)
            implementation(projects.data.notice)
            implementation(projects.data.division)

            implementation(libs.calf.file.picker)
            implementation(libs.calf.permissions)
            implementation(libs.urlencoder.lib)
        }
    }
}


androidLibrary {
    namespace = "com.b1nd.dodam.noticecreate"
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


