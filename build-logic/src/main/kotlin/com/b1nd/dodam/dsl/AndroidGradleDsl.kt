package com.b1nd.dodam.dsl

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.androidApplication(action: ApplicationExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.androidLibrary(action: LibraryExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.android(action: TestedExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.setupAndroid() {
    android {
        namespace?.let {
            this.namespace = it
        }
        compileSdkVersion(35)

        defaultConfig {
            minSdk = 28
            targetSdk = 35
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}
