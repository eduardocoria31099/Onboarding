package com.example.onboarding.service

import com.example.onboarding.service.character.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServiceDao {
    @GET("character")
    suspend fun getCharacter(): Response<CharacterResponse>
}