package com.b1nd.dodam.dsl

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

fun Project.kotlin(block: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure<KotlinMultiplatformExtension>(block)
}

fun Project.composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) {
    extensions.configure<ComposeCompilerGradlePluginExtension>(block)
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
fun Project.setupMultiplatform() {
    kotlin {
        // Task testClasses not found problem solve
        task("testClasses")
        androidTarget {
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
        // remove compiler warring
        sourceSets.commonMain {
            compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }

    android {
        namespace?.let {
            this.namespace = it
        }
        compileSdkVersion(34)

        defaultConfig {
            minSdk = 28
            targetSdk = 34
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}

fun KotlinMultiplatformExtension.setIOS(
    name: String,
    bundleId: String? = null,
    block: KotlinNativeTarget.() -> Unit = {}
) {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            export("io.github.mirzemehdi:kmpnotifier:1.3.0")
            baseName = name
            isStatic = true
            if (bundleId != null) {
                binaryOptions["bundleId"] = bundleId
            }
        }
        iosTarget.run(block)
    }
}