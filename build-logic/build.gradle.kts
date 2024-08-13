import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.b1nd.dodam.buildlogic"

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        // primitives
        register("androidApplication") {
            id = "b1nd.dodam.primitive.android.application"
            implementationClass = "com.b1nd.dodam.primitive.AndroidApplicationPlugin"
        }
        register("androidCompose") {
            id = "b1nd.dodam.primitive.android.compose"
            implementationClass = "com.b1nd.dodam.primitive.AndroidComposePlugin"
        }
        register("koin") {
            id = "b1nd.dodam.primitive.koin"
            implementationClass = "com.b1nd.dodam.primitive.KoinPlugin"
        }
        register("androidKoin") {
            id = "b1nd.dodam.primitive.android.koin"
            implementationClass = "com.b1nd.dodam.primitive.AndroidKoinPlugin"
        }
        register("androidKotlin") {
            id = "b1nd.dodam.primitive.android.kotlin"
            implementationClass = "com.b1nd.dodam.primitive.AndroidKotlinPlugin"
        }
        register("android") {
            id = "b1nd.dodam.primitive.android"
            implementationClass = "com.b1nd.dodam.primitive.AndroidPlugin"
        }
        register("kotlinSerialization") {
            id = "b1nd.dodam.primitive.kotlin.serialization"
            implementationClass = "com.b1nd.dodam.primitive.KotlinSerializationPlugin"
        }
        register("multiplatform") {
            id = "b1nd.dodam.primitive.multiplatform"
            implementationClass = "com.b1nd.dodam.primitive.MultiplatformPlugin"
        }
        register("multiplatformKotlin") {
            id = "b1nd.dodam.primitive.multiplatform.kotlin"
            implementationClass = "com.b1nd.dodam.primitive.MultiplatformKotlinPlugin"
        }
        register("multiplatformKotlinSerialization") {
            id = "b1nd.dodam.primitive.multiplatform.kotlin.serialization"
            implementationClass = "com.b1nd.dodam.primitive.MultiplatformKotlinSerializationPlugin"
        }
        register("multiplatformKoinPlugin") {
            id = "b1nd.dodam.primitive.multiplatform.koin"
            implementationClass = "com.b1nd.dodam.primitive.MultiplatformKoinPlugin"
        }
        register("multiplatformComposePlugin") {
            id = "b1nd.dodam.primitive.multiplatform.compose"
            implementationClass = "com.b1nd.dodam.primitive.MultiplatformComposePlugin"
        }
        // convention
        register("androidFeature") {
            id = "b1nd.dodam.convention.android.feature"
            implementationClass = "com.b1nd.dodam.convention.AndroidFeaturePlugin"
        }
        register("pureKotlin") {
            id = "b1nd.dodam.convention.kotlin"
            implementationClass = "com.b1nd.dodam.convention.PureKotlinPlugin"
        }
        register("multiplatformFeature") {
            id = "b1nd.dodam.convention.multiplatform.feature"
            implementationClass = "com.b1nd.dodam.convention.MultiplatformFeaturePlugin"
        }
    }
}
