package com.example.onboarding.viewmodel


data class UiState(
    val loading: Boolean = false,
    val message: String = "",
    val dismiss: Boolean = false,
)
