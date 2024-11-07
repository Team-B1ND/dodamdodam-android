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
        implementation("com.mohamedrejeb.calf:calf-file-picker:0.5.5")
        implementation("com.mohamedrejeb.calf:calf-permissions:0.5.5")
    }
}
android {
    namespace = "com.b1nd.dodam.edit_member_info"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}