package com.example.passwordmanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.passwordmanager.models.User


@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun addNewUser(user: User)

    @Query("SELECT COUNT(*) FROM users")
    abstract fun getUsersCount(): Int
}