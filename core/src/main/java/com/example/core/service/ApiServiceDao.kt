package com.example.core.service

import com.example.core.service.character.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServiceDao {
    @GET("character")
    suspend fun getCharacter(): Response<CharacterResponse>
}