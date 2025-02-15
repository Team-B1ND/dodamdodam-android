import com.b1nd.dodam.dsl.android
import com.b1nd.dodam.dsl.kotlin
import com.b1nd.dodam.dsl.setIOS

plugins {
    alias(libs.plugins.dodam.multiplatform.application)
    alias(libs.plugins.dodam.multiplatform.compose)
    alias(libs.plugins.dodam.multiplatform.kotlin)
    alias(libs.plugins.dodam.multiplatform.coil)
    alias(libs.plugins.dodam.multiplatform.koin)
    alias(libs.plugins.google.services)
}
kotlin {
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            export(libs.kmp.notification)
            baseName = "DodamTeacher"
            isStatic = true

            binaryOptions["bundleId"] = "com.b1nd.dodam.teacher"

        }
        iosTarget.run({})
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.dodam.design.system.cmm)
            implementation(projects.common)
            implementation(projects.ui)
            implementation(projects.network.login)
            api(libs.kmp.notification)
            implementation(projects.feature.onboarding)
            implementation(projects.featureTeacher.nightstudy)
            api(projects.feature.login)
            implementation(projects.datastore)
            implementation(projects.logging)
            implementation(projects.featureTeacher.register)
            implementation(projects.data.register)
            implementation(projects.network.register)
            implementation(projects.common)
            implementation(projects.network.core)
            implementation(projects.network.nightStudy)
            implementation(projects.featureTeacher.home)
            implementation(projects.feature.meal)
            implementation(projects.featureTeacher.outing)
            implementation(projects.featureTeacher.approveOuting)
            implementation(projects.featureTeacher.approveNightstudy)
            implementation(projects.featureTeacher.point)
            implementation(projects.featureTeacher.all)
            implementation(projects.feature.setting)
            implementation(projects.feature.notice)
            implementation(projects.featureTeacher.noticeCreate)
            implementation(projects.feature.featureGroupAlias)
            implementation(projects.feature.groupDetail)
            implementation(projects.feature.groupWaiting)
            implementation(projects.feature.groupCreate)
            implementation(projects.feature.groupAdd)
            implementation(projects.feature.editMemberInfo)


            implementation(projects.data.login)
            implementation(projects.data.banner)
            implementation(projects.data.meal)
            implementation(projects.data.outing)
            implementation(projects.data.nightStudy)
            implementation(projects.data.schedule)
            implementation(projects.data.member)
            implementation(projects.data.point)
            implementation(projects.data.upload)
            implementation(projects.data.bundleidInfo)
            implementation(projects.data.division)

            implementation(projects.network.banner)
            implementation(projects.network.meal)
            implementation(projects.network.outing)
            implementation(projects.network.nightStudy)
            implementation(projects.network.schedule)
            implementation(projects.network.member)
            implementation(projects.network.point)
            implementation(projects.network.bundleidInfo)
            implementation(projects.network.upload)
            implementation(projects.network.division)

            androidMain.dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.compose.activity)
                implementation(libs.koin.android)
                implementation(projects.keystore)
                implementation(libs.google.app.update)
            }
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
        versionCode = 3
        versionName = "3.1.0"

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures.compose = true
}

