package com.b1nd.dodam.register.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterParentRequest (
    val id: String,
    val pw: String,
    val name: String,
    val relationInfo: List<ChildrenRequest>,
    val phone: String
)

@Serializable
data class ChildrenRequest(
    val name: String,
    val relation: String
)