package com.example.onboarding.viewmodel

import com.example.onboarding.domain.Result
import com.example.onboarding.model.UserEntity

data class UiState(
    val loading: Boolean = false,
    val message: String = "",
    val dismiss: Boolean = false,
    val userEntity: UserEntity = UserEntity(id = null, name = "", mail = "", password = ""),
    val nextActivity: Boolean = false,
    val vip: Boolean = false,
    val visibility: Boolean = false,
    val character: List<Result> = emptyList(),
)
