package com.example.core.domain

import com.example.core.model.UserEntity

data class User(
    val id: Int?,
    val name: String?,
    val mail: String?,
    val password: String?,
)

fun UserEntity.toDomain() = User(id, name, mail, password)
