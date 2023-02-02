package com.example.onboarding.domain

import com.example.onboarding.service.character.CharacterModel


data class Result(
    val image: String?,
    val name: String?,
)

fun CharacterModel.toDomain() = Result(image, name)