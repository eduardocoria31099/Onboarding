package com.example.onboarding

import android.app.Application
import androidx.room.Room
import com.example.onboarding.data.database.AppDatabase
import com.example.utils.Constants

class App : Application() {

    companion object {
        lateinit var database: AppDatabase
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