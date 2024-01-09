plugins {
    id("b1nd.dodam.primitive.android")
    id("b1nd.dodam.primitive.android.kotlin")
    id("b1nd.dodam.primitive.android.compose")
}

android {
    namespace = "com.b1nd.dodam.designsystem"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
