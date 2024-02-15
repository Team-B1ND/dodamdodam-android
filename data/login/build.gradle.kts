plugins {
    alias(libs.plugins.dodam.kotlin)
}

dependencies {
    api(projects.model)
    implementation(projects.network.core)
    implementation(projects.network.login)
}
