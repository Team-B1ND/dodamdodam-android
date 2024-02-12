plugins {
    alias(libs.plugins.dodam.kotlin)
    alias(libs.plugins.dodam.kotlin.serialization)
}

dependencies {
    api(projects.network.core)
    implementation(projects.common)

    implementation(libs.kotlinx.collections.immutable)

    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}