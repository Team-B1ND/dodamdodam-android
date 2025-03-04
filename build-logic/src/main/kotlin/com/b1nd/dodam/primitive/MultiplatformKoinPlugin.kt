package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import com.b1nd.dodam.dsl.setupMultiplatform
import org.gradle.api.Plugin
import org.gradle.api.Project

class MultiplatformKoinPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            kotlin {
                sourceSets.commonMain.dependencies {
                    implementation(libs.library("koin-core"))
                    implementation(libs.library("koin-compose-multiplatform"))
                    implementation(libs.library("koin-compose-viewmodel"))
                }
            }
        }
    }
}