package com.example.onboarding.viewmodel

import com.example.core.domain.Result
import com.example.core.domain.User

data class UiState(
    val loading: Boolean = false,
    val message: String = "",
    val dismiss: Boolean = false,
    val user: User = User(id = null, name = "", mail = "", password = ""),
    val nextActivity: Boolean = false,
    val vip: Boolean = false,
    val visibility: Boolean = false,
    val character: List<Result> = emptyList(),
)
