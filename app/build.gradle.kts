plugins {
    id("b1nd.dodam.primitive.android.application")
    id("b1nd.dodam.primitive.android.kotlin")
    id("b1nd.dodam.primitive.android.compose")
    id("b1nd.dodam.primitive.android.hilt")
}

android {
    namespace = "com.b1nd.dodam"

    defaultConfig {
        applicationId = "com.b1nd.dodam"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(platform(libs.androidx.compose.bom))
}
