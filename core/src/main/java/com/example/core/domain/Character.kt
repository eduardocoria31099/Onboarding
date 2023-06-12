package com.example.core.domain

import com.example.core.service.character.CharacterModel


data class Result(
    val image: String?,
    val name: String?,
)

fun CharacterModel.toDomain() = Result(image, name)