package com.b1nd.dodam.dsl

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


fun Project.androidKotlin(block: KotlinAndroidProjectExtension.() -> Unit) {
    extensions.configure<KotlinAndroidProjectExtension>(block)
}

fun DependencyHandlerScope.ksp(
    artifact: MinimalExternalModuleDependency,
) {
    add("ksp", artifact)
}
