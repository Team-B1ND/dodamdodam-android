import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform.application)
    alias(libs.plugins.dodam.multiplatform.compose)
    alias(libs.plugins.dodam.multiplatform.kotlin)
    alias(libs.plugins.dodam.multiplatform.koin)
}
kotlin {
    setIOS("DodamTeacher", "com.b1nd.dodam.teacher")

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.compose.activity)
            implementation(libs.koin.android)
            implementation(projects.keystore)
        }
        commonMain.dependencies {
            implementation(libs.dodam.design.system.cmm)
            implementation(projects.common)
            implementation(projects.network.login)

            implementation(projects.feature.onboarding)
            api(projects.feature.login)
            implementation(projects.datastore)
            implementation(projects.logging)
            implementation(projects.featureTeacher.register)
            implementation(projects.data.register)
            implementation(projects.network.register)
            implementation(projects.common)
            implementation(projects.network.core)
        }
    }
}

android {
    namespace = "com.b1nd.dodam.teacher"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.b1nd.dodam.teacher"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures.compose = true
}

