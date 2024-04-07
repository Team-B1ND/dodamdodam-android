package com.b1nd.dodam.member

import com.b1nd.dodam.member.model.MyInfo

data class AllUiState(
    val myInfo: MyInfo? = null,
    val isLoading: Boolean = false,
    val isSimmer: Boolean = true,
)
