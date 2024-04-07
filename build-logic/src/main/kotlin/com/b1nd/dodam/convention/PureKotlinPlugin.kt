package com.b1nd.dodam.convention

import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
class PureKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("java-library")
            apply("org.jetbrains.kotlin.jvm")
        }
        dependencies {
            implementation(libs.library("kotlinx-coroutines-core"))
        }
    }
}