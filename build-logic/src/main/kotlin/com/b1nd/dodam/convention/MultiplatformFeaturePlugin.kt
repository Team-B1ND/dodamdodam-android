package com.b1nd.dodam.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class MultiplatformFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("b1nd.dodam.primitive.multiplatform")
                apply("b1nd.dodam.primitive.multiplatform.compose")
                apply("b1nd.dodam.primitive.multiplatform.kotlin")
            }
        }
    }
}