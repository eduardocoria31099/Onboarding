package com.example.onboarding.service.character

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info") val info: Info?,
    @SerializedName("results") val characterModels: List<CharacterModel>,
)