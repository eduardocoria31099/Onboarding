package com.example.onboarding.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private var dataStore: DataStore<Preferences>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(dataStore) as T
        }
        throw java.lang.IllegalArgumentException("ViewModel class not fond")
    }
}