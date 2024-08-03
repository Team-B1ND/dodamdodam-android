package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MultiplatformKotlinSerializationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
            kotlin {
                sourceSets.commonMain {
                    dependencies {
                        implementation(libs.library("kotlinx-serialization-json"))
                    }
                }
            }
        }
    }
}