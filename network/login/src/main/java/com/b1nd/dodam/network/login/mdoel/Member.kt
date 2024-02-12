package com.b1nd.dodam.network.login.mdoel

data class Member(
    val email: String,
    val id: String,
    val joinDate: String,
    val name: String,
    val profileImage: String,
    val role: String,
    val status: String,
    val student: Student
)