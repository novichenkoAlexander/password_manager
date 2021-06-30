package com.example.passwordmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.passwordmanager.BuildConfig
import com.example.passwordmanager.models.Note
import com.example.passwordmanager.models.User


@Database(
    entities = [
        Note::class,
        User::class
    ],
    version = 1,
    exportSchema = false
)

abstract class PasswordAppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun userDao(): UserDao
}

object DataBaseConstructor {
    fun create(context: Context): PasswordAppDatabase =
        Room.databaseBuilder(
            context,
            PasswordAppDatabase::class.java,
            "${BuildConfig.APPLICATION_ID}.db"
        ).build()
}