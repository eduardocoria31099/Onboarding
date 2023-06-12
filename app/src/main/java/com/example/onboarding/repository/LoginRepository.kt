package com.example.onboarding.repository

import com.example.core.App
import com.example.core.domain.toDomain
import com.example.core.model.UserEntity
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

    fun readUser(mail: String, password: String) = flow {
        emit(ApiResponseStatus.Loading())
        try {
            emit(ApiResponseStatus.Success(App.database.userDao().readUser(mail, password)?.toDomain()))
        } catch (ex: java.lang.Exception) {
            emit(ApiResponseStatus.Error(ex.message ?: ""))
        }
    }.flowOn(Dispatchers.IO)
}