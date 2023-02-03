package com.example.onboarding.repository

import com.example.onboarding.domain.toDomain
import com.example.onboarding.service.ApiService
import com.example.utils.ApiResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepository {

    suspend fun getCharacters() = flow {
        emit(ApiResponseStatus.Loading())
        try {
            val response = ApiService.retrofitService.getCharacter()
            if (response.isSuccessful) {
                emit(ApiResponseStatus.Success(
                    response.body()?.characterModels?.map { it.toDomain() } ?: emptyList()
                ))
            }
        } catch (ex: Exception) {
            emit(ApiResponseStatus.Error(ex.message ?: ""))
        }
    }.flowOn(Dispatchers.IO)
}