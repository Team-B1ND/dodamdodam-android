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
                apply("com.android.application")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.multiplatform")
            }
            setupMultiplatform()

            kotlin {
                sourceSets.commonMain.dependencies {
                    implementation(libs.library("androidx-lifecycle-viewmodel-compose"))
                    implementation(libs.library("koin-compose-multiplatform"))
                    implementation(libs.library("koin-compose-viewmodel"))
                }
            }
        }
    }
}