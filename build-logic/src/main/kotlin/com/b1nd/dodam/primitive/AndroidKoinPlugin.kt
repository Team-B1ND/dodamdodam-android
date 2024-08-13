package com.b1nd.dodam.primitive

import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.androidTestImplementation
import com.b1nd.dodam.dsl.debugImplementation
import com.b1nd.dodam.dsl.library
import com.b1nd.dodam.dsl.libs
import com.b1nd.dodam.dsl.version
import com.b1nd.dodam.dsl.implementation
import com.b1nd.dodam.dsl.implementationPlatform
import com.b1nd.dodam.dsl.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidKoinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(libs.library("koin-android"))
                implementation(libs.library("koin-core"))
                implementation(libs.library("koin-compose"))
            }
        }
    }

}