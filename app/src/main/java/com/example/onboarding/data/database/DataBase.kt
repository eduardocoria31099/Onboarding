package com.example.onboarding.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.onboarding.data.dao.UserDao
import com.example.onboarding.model.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}