package com.b1nd.dodam.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("b1nd.dodam.primitive.android")
                apply("b1nd.dodam.primitive.android.kotlin")
                apply("b1nd.dodam.primitive.android.compose")
            }
        }
    }
}