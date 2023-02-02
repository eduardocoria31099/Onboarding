package com.example.onboarding.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.onboarding.data.dao.PersonDao
import com.example.onboarding.data.dao.UserDao
import com.example.onboarding.model.PersonEntity
import com.example.onboarding.model.UserEntity

@Database(
    entities = [UserEntity::class,PersonEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun personDao(): PersonDao
}