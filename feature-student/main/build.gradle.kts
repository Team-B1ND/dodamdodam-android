@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.dodam.android.feature)
}

android {
    namespace = "com.b1nd.dodam.student.main"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.featureStudent.home)
    implementation(projects.feature.meal)
    implementation(projects.featureStudent.nightstudy)
    implementation(projects.featureStudent.outing)
    implementation(projects.featureStudent.all)
}