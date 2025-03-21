import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform.feature)
    alias(libs.plugins.dodam.multiplatform.koin)
    alias(libs.plugins.dodam.multiplatform.coil)
}

kotlin {

    setIOS(
        name = "edit_member_info",
        bundleId = "com.b1nd.dodam.edit_member_info"
    )

    sourceSets.commonMain.dependencies {
        implementation(libs.dodam.design.system.cmm)
        implementation(projects.data.member)
        implementation(projects.common)
        implementation(projects.ui)
        implementation(projects.logging)
        implementation(projects.data.upload)
        implementation(libs.calf.file.picker)
        implementation(libs.calf.permissions)
        implementation(libs.urlencoder.lib)
    }
}
android {
    namespace = "com.b1nd.dodam.edit_member_info"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}