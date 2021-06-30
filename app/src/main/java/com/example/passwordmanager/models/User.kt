package com.example.passwordmanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "password")
    val password: Long
)