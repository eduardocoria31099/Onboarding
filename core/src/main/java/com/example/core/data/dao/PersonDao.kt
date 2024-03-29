package com.example.core.data.dao

import androidx.room.*
import com.example.core.model.PersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert
    fun insert(personEntity: PersonEntity)

    @Query("SELECT * FROM person_table")
    fun getAll(): Flow<List<PersonEntity>>

    @Delete
    fun delete(personEntity: PersonEntity)

    @Update
    fun update(personEntity: PersonEntity)
}