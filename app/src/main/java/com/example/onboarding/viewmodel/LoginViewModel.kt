package com.example.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding.model.UserEntity
import com.example.onboarding.repository.LoginRepository
import com.example.utils.ApiResponseStatus
import com.example.utils.Utils.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var repository = LoginRepository()
    private val _state = MutableStateFlow(UiState())

    val state: StateFlow<UiState> get() = _state

    fun validateFields(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        when {
            name.isEmpty() -> _state.update { it.copy(message = "Name is empty") }
            email.isEmpty() -> _state.update { it.copy(message = "Email is empty") }
            !isValidEmail(email) -> _state.update { it.copy(message = "it is not a valid email") }
            password.isEmpty() -> _state.update { it.copy(message = "Password is empty") }
            confirmPassword.isEmpty() -> _state.update { it.copy(message = "Confirm Password is empty") }
            confirmPassword != password -> _state.update { it.copy(message = "Passwords do not match") }
            else -> createAccount(UserEntity(0, name = name, mail = email, password = password))
        }
        resetMessage()
    }


    private fun createAccount(userEntity: UserEntity) = viewModelScope.launch {
        repository.createAccount(userEntity).collect { response ->
            when (response) {
                is ApiResponseStatus.Loading -> {
                    _state.update {
                        it.copy(
                            loading = true,
                            message = "",
                            dismiss = false,
                        )
                    }
                }
                is ApiResponseStatus.Success -> {
                    _state.update {
                        it.copy(
                            loading = false,
                            message = "Account created successfully",
                            dismiss = true,
                        )
                    }
                }
                is ApiResponseStatus.Error -> {
                    _state.update {
                        it.copy(
                            loading = true,
                            message = "",
                            dismiss = false,
                        )
                    }
                }
            }
            resetMessage()
        }
    }

    fun resetMessage() {
        _state.update { it.copy(message = "") }
    }
}