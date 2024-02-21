plugins {
    alias(libs.plugins.dodam.kotlin)
    alias(libs.plugins.dodam.hilt)
}

dependencies {
    api(projects.model)
    implementation(projects.common)
    implementation(projects.network.core)
    implementation(projects.network.login)
}
