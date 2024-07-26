package com.b1nd.dodam.common

@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcherType: DispatcherType)

enum class DispatcherType {
    Default,
    IO,
}
