package com.example.onboarding.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val img: String?,
    val name: String?,
    val birthday: String?,
    val address: String?,
    val number: String?,
    val hobbies: String?,
)
