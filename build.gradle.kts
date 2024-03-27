import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.jvm).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.dagger.hilt).apply(false)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.spotless).apply(false)
    alias(libs.plugins.android.library).apply(false)
}

subprojects {
    plugins.apply(rootProject.libs.plugins.spotless.get().pluginId)

    extensions.configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt", "**/test/**.kt", "**/androidTest/**.kt")
            targetExclude("${layout.buildDirectory}/**/*.kt")
            ktlint()
                .setEditorConfigPath("${project.rootDir}/spotless/.editorconfig")
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}
