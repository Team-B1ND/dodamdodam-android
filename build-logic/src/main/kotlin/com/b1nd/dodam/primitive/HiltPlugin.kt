package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.ksp
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }
            dependencies {
                implementation(libs.library("hilt-core"))
                ksp(libs.library("hilt-compiler"))
            }
        }
    }

}