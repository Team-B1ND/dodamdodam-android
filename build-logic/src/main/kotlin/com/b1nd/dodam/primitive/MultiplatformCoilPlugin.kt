package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import com.b1nd.dodam.dsl.setupMultiplatform
import org.gradle.api.Plugin
import org.gradle.api.Project

class MultiplatformCoilPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            kotlin {
                sourceSets.commonMain.dependencies {
                    implementation(libs.library("kotlinx-serialization-json"))
                    implementation(libs.library("kotlinx-io-core"))
                    implementation(libs.library("kotlinx-io-bytestring"))
                    implementation(libs.library("coil"))
                    implementation(libs.library("coil.compose"))
                    implementation(libs.library("coil.network.ktor"))
                    implementation(libs.library("ktor.client.core"))
                }

                sourceSets.androidMain.dependencies {
                    implementation(libs.library("coil.network.okhttp"))
                }
                sourceSets.iosMain.dependencies {
                    implementation(libs.library("ktor.client.darwin"))
                }
            }
        }
    }
}