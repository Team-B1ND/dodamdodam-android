import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.dodam.multiplatform)
    alias(libs.plugins.dodam.multiplatform.kotlin)
    alias(libs.plugins.dodam.multiplatform.compose)
}

kotlin{
    setIOS(name = "designsystem")

    sourceSets{
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
        }
        androidMain.dependencies {
            api(libs.androidx.compose.material)
        }
    }

    androidTarget{
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

android {
    namespace = "com.b1nd.dodam.designsystem"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

