package com.b1nd.dodam.model

data class Outing(
    val id: Long,
    val reason: String,
    val type: OutType,
    val status: Status,
    val student: Student,
    val teacher: Teacher,
    val startOutDate: String,
    val endOutDate: String?,
    val arrivedDate: String?,
    val checkedDate: String,
)
