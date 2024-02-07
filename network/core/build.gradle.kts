plugins {
    alias(libs.plugins.dodam.kotlin)
    alias(libs.plugins.dodam.kotlin.serialization)
}

dependencies {
    api(libs.ktor.client.core)
    implementation(libs.ktor.client.logging.jvm)
    implementation(libs.ktor.client.cio)
    api(libs.ktor.client.content.negotiation)
    api(libs.ktor.serialization.kotlinx.json)

    api(libs.hilt.core)
}
