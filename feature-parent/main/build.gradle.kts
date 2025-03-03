@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.dodam.android.feature)
    alias(libs.plugins.dodam.android.koin)
}

android {
    namespace = "com.b1nd.dodam.panret.main"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.dodam.design.system.cmm)
    implementation(projects.feature.meal)
    implementation(projects.feature.notice)
    implementation(projects.featureParent.home)
    implementation(projects.featureParent.all)
    implementation(projects.featureParent.childrenManage)
    implementation(projects.featureStudent.register)
    implementation(projects.feature.setting)
}