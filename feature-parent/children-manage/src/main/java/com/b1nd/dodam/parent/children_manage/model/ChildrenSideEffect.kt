package com.b1nd.dodam.parent.children_manage.model

interface ChildrenSideEffect {
    data object SuccessGetChildren : ChildrenSideEffect
    data class Failed(val throwable: Throwable) : ChildrenSideEffect
}