package com.example.onboarding.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.onboarding.model.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insert(userEntity: UserEntity)
}