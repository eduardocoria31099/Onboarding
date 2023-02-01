package com.example.onboarding.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding.App.Companion.datastore
import com.example.onboarding.model.UserEntity
import com.example.onboarding.repository.LoginRepository
import com.example.utils.ApiResponseStatus
import com.example.utils.Utils.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(@field:SuppressLint("StaticFieldLeak") val context: Context) : ViewModel() {

    private var repository = LoginRepository()
    private val _state = MutableStateFlow(UiState())

    val state: StateFlow<UiState> get() = _state

    fun validateFieldsRegister(
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
            else -> createAccount(
                UserEntity(
                    id = null,
                    name = name,
                    mail = email,
                    password = password
                )
            )
        }
        resetMessage()
    }


    private fun createAccount(userEntity: UserEntity) = viewModelScope.launch {
        repository.createAccount(userEntity).collect { response ->
            when (response) {
                is ApiResponseStatus.Loading -> {
                    _state.update {
                        it.copy(
                            message = "",
                            dismiss = false,
                        )
                    }
                }
                is ApiResponseStatus.Success -> {
                    _state.update {
                        it.copy(
                            message = "Account created successfully",
                            dismiss = true,
                        )
                    }
                }
                is ApiResponseStatus.Error -> {
                    _state.update {
                        it.copy(
                            message = response.message,
                            dismiss = false,
                        )
                    }
                }
            }
            resetMessage()
        }
    }

    fun validateFieldsLogin(
        email: String,
        password: String,
    ) {
        when {
            email.isEmpty() -> _state.update { it.copy(message = "email is empty") }
            password.isEmpty() -> _state.update { it.copy(message = "password is empty") }
            else -> readUser(email, password)
        }
        resetMessage()
    }

    private fun readUser(
        mail: String,
        password: String
    ) = viewModelScope.launch {
        repository.readUser(mail, password).flowOn(Dispatchers.IO).collect { response ->
            when (response) {
                is ApiResponseStatus.Loading -> {
                    _state.update {
                        it.copy(
                            nextActivity = false,
                        )
                    }
                }
                is ApiResponseStatus.Success -> {
                    context.datastore.edit {
                        it[booleanPreferencesKey("vip")] = true
                    }
                    response.data?.let { user ->
                        _state.update {
                            it.copy(
                                userEntity = user,
                                nextActivity = true
                            )
                        }
                    }
                }
                is ApiResponseStatus.Error -> {
                    _state.update {
                        it.copy(
                            message = response.message,
                            nextActivity = false,
                        )
                    }
                }
            }
            resetMessage()
        }
    }

    private fun resetMessage() {
        _state.update { it.copy(message = "") }
    }

    fun resetValues() {
        _state.update { it.copy(message = "", dismiss = false, nextActivity = false) }
    }
}