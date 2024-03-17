plugins {
    alias(libs.plugins.dodam.android.application)
    alias(libs.plugins.dodam.android.kotlin)
    alias(libs.plugins.dodam.android.compose)
    alias(libs.plugins.dodam.android.hilt)
}

android {
    namespace = "com.b1nd.dodam.test"

    defaultConfig {
        applicationId = "com.b1nd.dodam.test"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.appVersion.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(projects.designsystem)
    implementation(projects.featureStudent.main)
    implementation(projects.featureStudent.nightstudy)
}
