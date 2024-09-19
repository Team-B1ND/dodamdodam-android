package com.b1nd.dodam.all.model

import com.b1nd.dodam.member.model.ActiveStatus
import com.b1nd.dodam.member.model.MemberInfo

data class AllUiState(
    val isLoading: Boolean = true,
    val memberInfo: MemberInfo = MemberInfo(
        createdAt = "",
        email = "",
        id = "",
        modifiedAt = "",
        name = "",
        phone = "",
        profileImage = null,
        role = "",
        status = ActiveStatus.ACTIVE,
        student = null,
        teacher = null
    )
)