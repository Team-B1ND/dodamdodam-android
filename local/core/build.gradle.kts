import com.b1nd.dodam.dsl.android
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.dodam.android)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.kotlin.serialization)
    alias(libs.plugins.dodam.android.hilt)
    alias(libs.plugins.sqldelight)
}

sqldelight {
    databases {
        create("TestDatabase") {
            packageName.set("com.b1nd.database")
        }
    }
}

android {
    namespace = "com.b1nd.dodam.local.core"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.network.core)
    implementation(projects.common)

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.sqldelight.driver)
    implementation(libs.sqldelight.coroutines)
}
