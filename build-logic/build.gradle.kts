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
        register("androidHilt") {
            id = "b1nd.dodam.primitive.android.hilt"
            implementationClass = "com.b1nd.dodam.primitive.AndroidHiltPlugin"
        }
        register("hilt") {
            id = "b1nd.dodam.primitive.hilt"
            implementationClass = "com.b1nd.dodam.primitive.HiltPlugin"
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
        register("hilt") {
            id = "b1nd.dodam.primitive.hilt"
            implementationClass = "com.b1nd.dodam.primitive.HiltPlugin"
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
    }
}
