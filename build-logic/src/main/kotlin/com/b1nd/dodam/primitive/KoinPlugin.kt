package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(libs.library("koin-android"))
                implementation(libs.library("koin-core"))
            }
        }
    }
}