package com.example.core

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.core.data.database.AppDatabase
import com.example.utils.Constants

class App : Application() {

    companion object {
        lateinit var database: AppDatabase
        val Context.datastore by preferencesDataStore(name = "LOGIN_PREFERENCES")
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}