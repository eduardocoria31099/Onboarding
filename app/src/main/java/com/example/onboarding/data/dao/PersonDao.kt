package com.example.onboarding.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.onboarding.model.PersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert
    fun insert(personEntity: PersonEntity)

    @Query("SELECT * FROM person_table")
    fun getAll(): Flow<List<PersonEntity>>
}