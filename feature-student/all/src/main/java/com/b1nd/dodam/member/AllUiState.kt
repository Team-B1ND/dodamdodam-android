package com.b1nd.dodam.member

import com.b1nd.dodam.member.model.MemberInfo

data class AllUiState(
    val memberInfo: MemberInfo? = null,
    val isLoading: Boolean = false,
    val isSimmer: Boolean = true,
)
