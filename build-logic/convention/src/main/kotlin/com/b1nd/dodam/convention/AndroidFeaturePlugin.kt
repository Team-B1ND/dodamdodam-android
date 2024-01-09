package com.b1nd.dodam.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("b1nd.dodamdodam.primitive.android")
                apply("b1nd.dodamdodam.primitive.android.kotlin")
                apply("b1nd.dodamdodam.primitive.android.compose")
                apply("b1nd.dodamdodam.primitive.android.hilt")
            }
        }
    }
}