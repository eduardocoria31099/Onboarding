package com.example.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding.model.PersonEntity
import com.example.onboarding.repository.ContainerRepository
import com.example.utils.ApiResponseStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContainerViewModel : ViewModel() {

    private var repository = ContainerRepository()
    val inventory: Flow<List<PersonEntity>> get() = repository.getAll()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state

    fun validateFields(
        img: String,
        name: String,
        birthday: String,
        address: String,
        number: String,
        hobbies: String,
    ) {
        when {
            img.isEmpty() -> _state.update { it.copy(visibility = false) }
            name.isEmpty() -> _state.update { it.copy(visibility = false) }
            birthday.isEmpty() -> _state.update { it.copy(visibility = false) }
            address.isEmpty() -> _state.update { it.copy(visibility = false) }
            number.isEmpty() -> _state.update { it.copy(visibility = false) }
            else -> _state.update { it.copy(visibility = true) }
        }
    }

    fun savePerson(personEntity: PersonEntity) = viewModelScope.launch {
        repository.addPerson(personEntity).collect { response ->
            when (response) {
                is ApiResponseStatus.Loading -> {}
                is ApiResponseStatus.Success -> {
                    _state.update {
                        it.copy(
                            message = "Person added successfully",
                        )
                    }
                }
                is ApiResponseStatus.Error -> {}
            }
            resetMessage()
        }
    }

    fun resetMessage() {
        _state.update { it.copy(message = "") }
    }
}