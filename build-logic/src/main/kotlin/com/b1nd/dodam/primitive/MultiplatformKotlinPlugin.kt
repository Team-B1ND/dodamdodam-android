package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.kotlinOptions
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@OptIn(ExperimentalKotlinGradlePluginApi::class)
class MultiplatformKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            tasks.withType(KotlinCompile::class.java) {
                kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
            }

            kotlin {
                compilerOptions {
                    freeCompilerArgs.addAll(
                        listOf(
                            "-opt-in=kotlin.RequiresOptIn",
                            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                            "-Xcontext-receivers"
                        )
                    )
                }
                sourceSets.commonMain.dependencies {
                    implementation(libs.library("kotlinx-coroutines-core"))
                    implementation(libs.library("kotlinx-collections-immutable"))
                }
            }
        }
    }
}