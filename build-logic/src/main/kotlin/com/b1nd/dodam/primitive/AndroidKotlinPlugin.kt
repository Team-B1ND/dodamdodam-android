package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.androidKotlin
import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidKotlinPlugin : Plugin<Project> {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
            }

            tasks.withType(KotlinCompile::class.java) {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }

            android {
                androidKotlin {
                    compilerOptions {
                        freeCompilerArgs.addAll(
                            listOf(
                                "-opt-in=kotlin.RequiresOptIn",
                                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                                "-Xcontext-receivers"
                            )
                        )
                    }
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_17)
                    }
                }
            }
            dependencies {
                implementation(libs.library("kotlinx-coroutines-core"))
                implementation(libs.library("kotlinx-collections-immutable"))
            }
        }
    }
}