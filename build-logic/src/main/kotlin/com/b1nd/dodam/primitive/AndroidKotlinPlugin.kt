package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.kotlinOptions
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
            }
            tasks.withType(KotlinCompile::class.java) {
                kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
            }

            android {
                kotlinOptions {
                    freeCompilerArgs = freeCompilerArgs + listOf(
                        "-opt-in=kotlin.RequiresOptIn",
                        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                        "-Xcontext-receivers"
                    )

                    jvmTarget = JavaVersion.VERSION_17.toString()
                }
            }
            dependencies {
                implementation(libs.library("kotlinx-coroutines-core"))
                implementation(libs.library("kotlinx-collections-immutable"))
            }
        }
    }
}