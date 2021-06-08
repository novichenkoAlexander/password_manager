package com.example.passwordmanager.database

import androidx.room.*
import com.example.passwordmanager.models.Note


@Dao
abstract class NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNotes(notes: List<Note>)

    @Delete
    abstract fun deleteNote(note: Note)

    @Update
    abstract fun updateNote(note: Note)

}