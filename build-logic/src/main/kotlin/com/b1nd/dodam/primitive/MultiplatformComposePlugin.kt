package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import com.b1nd.dodam.dsl.setupMultiplatform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MultiplatformComposePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            kotlin {
                sourceSets.commonMain.dependencies {
                    implementation(libs.library("androidx-lifecycle-viewmodel-compose"))
                    implementation(libs.library("multiplatform-compose-material3"))
                    implementation(libs.library("multiplatform-compose-navigation"))
                    implementation(libs.library("multiplatform-compose-components-resources"))
                }

                sourceSets.androidMain.dependencies {
                    implementation(libs.library("androidx-compose-ui-tooling-preview"))
                    implementation(libs.library("androidx-compose-ui-tooling"))
                }
            }
        }
    }
}