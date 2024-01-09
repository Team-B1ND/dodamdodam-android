package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.androidLibrary
import com.b1nd.dodam.dsl.setupAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            androidLibrary {
                setupAndroid()
            }
        }
    }
}