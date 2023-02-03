package com.example.onboarding.repository

import com.example.onboarding.App
import com.example.onboarding.model.PersonEntity
import com.example.utils.ApiResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ContainerRepository {

    fun addPerson(personEntity: PersonEntity) = flow {
        emit(ApiResponseStatus.Loading())
        try {
            emit(ApiResponseStatus.Success(App.database.personDao().insert(personEntity)))
        } catch (ex: Exception) {
            emit(ApiResponseStatus.Error(ex.message ?: ""))
        }
    }.flowOn(Dispatchers.IO)

    fun getAll(): Flow<List<PersonEntity>> {
        return App.database.personDao().getAll()
    }

    fun deletePerson(personEntity: PersonEntity) = flow {
        emit(ApiResponseStatus.Loading())
        try {
            emit(ApiResponseStatus.Success(App.database.personDao().delete(personEntity)))
        } catch (ex: Exception) {
            emit(ApiResponseStatus.Error(ex.message ?: ""))
        }
    }.flowOn(Dispatchers.IO)
}