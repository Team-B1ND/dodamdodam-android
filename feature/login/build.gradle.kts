plugins {
    alias(libs.plugins.dodam.android.feature)
}

android {
    namespace = "kr.hs.dgsw.login"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(libs.bottomsheetdialog.compose)
}