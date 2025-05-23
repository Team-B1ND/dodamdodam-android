@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
}

android {
    namespace = "com.b1nd.dodam.student.main"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.dodam.design.system.cmm)
    implementation(projects.featureStudent.home)
    implementation(projects.feature.meal)
    implementation(projects.featureStudent.nightstudy)
    implementation(projects.featureStudent.outing)
    implementation(projects.featureStudent.all)
    implementation(projects.feature.notice)
}