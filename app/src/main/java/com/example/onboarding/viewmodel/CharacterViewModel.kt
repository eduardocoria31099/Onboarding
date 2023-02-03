package com.example.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding.repository.CharacterRepository
import com.example.utils.ApiResponseStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {

    private var repository = CharacterRepository()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state

    suspend fun getCharacter() = viewModelScope.launch {
        repository.getCharacters().collect { response ->
            when (response) {
                is ApiResponseStatus.Loading -> _state.update { it.copy(loading = true) }
                is ApiResponseStatus.Success -> response.data?.let { character ->
                    _state.update {
                        it.copy(
                            loading = false,
                            character = character,
                            message = "Success"
                        )
                    }
                }
                is ApiResponseStatus.Error -> response.message.let { message ->
                    _state.update {
                        it.copy(loading = false, message = message)
                    }
                }
            }
        }
    }
}