package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.ksp
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }
            android {
                packagingOptions {
                    resources {
                        excludes += "META-INF/gradle/incremental.annotation.processors"
                    }
                }
            }
            dependencies {
                implementation(libs.library("hilt"))
                ksp(libs.library("hilt-compiler"))
            }
        }
    }

}