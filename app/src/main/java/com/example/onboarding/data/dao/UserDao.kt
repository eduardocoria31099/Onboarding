package com.example.onboarding.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.onboarding.model.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM user_table WHERE mail=:mail and password=:password")
    fun readUser(mail: String, password: String): UserEntity?
}