import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform)
}

kotlin {
    setIOS("common")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}


android {
    namespace = "com.b1nd.dodam.common"
}