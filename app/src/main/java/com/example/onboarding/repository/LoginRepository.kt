package com.example.onboarding.repository

import com.example.onboarding.App
import com.example.onboarding.model.UserEntity
import com.example.utils.ApiResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository {

    fun createAccount(userEntity: UserEntity) = flow {
        emit(ApiResponseStatus.Loading())
        try {
            emit(ApiResponseStatus.Success(App.database.userDao().insert(userEntity)))
        } catch (ex: Exception) {
            emit(ApiResponseStatus.Error(ex.message ?: ""))
        }
    }.flowOn(Dispatchers.IO)
}