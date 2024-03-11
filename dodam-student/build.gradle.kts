plugins {
    alias(libs.plugins.dodam.android.application)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.android.compose)
    alias(libs.plugins.dodam.android.hilt)
}

android {
    namespace = "com.b1nd.dodam.student"

    defaultConfig {
        applicationId = "com.b1nd.dodam.student"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.appVersion.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(projects.designsystem)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(projects.feature.onboarding)
    implementation(projects.feature.register)
    implementation(projects.feature.login)
    implementation(projects.feature.meal)
}
