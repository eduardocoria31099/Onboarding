package com.example.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.dao.PersonDao
import com.example.core.data.dao.UserDao
import com.example.core.model.PersonEntity
import com.example.core.model.UserEntity

@Database(
    entities = [UserEntity::class, PersonEntity::class],
    version = 2
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun personDao(): PersonDao
}