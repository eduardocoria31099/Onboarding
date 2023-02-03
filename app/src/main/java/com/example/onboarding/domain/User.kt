package com.example.onboarding.domain

import com.example.onboarding.model.UserEntity

data class User(
    val id: Int?,
    val name: String?,
    val mail: String?,
    val password: String?,
)

fun UserEntity.toDomain() = User(id, name, mail, password)
