import com.b1nd.dodam.dsl.setIOS
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.dodam.multiplatform.feature)
}

kotlin{
    androidTarget{
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

}

    setIOS(
        name = "ui",
        bundleId = "com.b1nd.dodam.ui"
    )
android {
    namespace = "com.b1nd.dodam.ui"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
