package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import com.b1nd.dodam.dsl.setupMultiplatform
import org.gradle.api.Plugin
import org.gradle.api.Project

class MultiplatformPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
            }
            setupMultiplatform()
        }
    }
}