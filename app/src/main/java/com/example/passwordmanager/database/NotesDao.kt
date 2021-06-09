package com.example.passwordmanager.database

import androidx.room.*
import com.example.passwordmanager.models.Note
import kotlinx.coroutines.flow.Flow


@Dao
abstract class NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNotes(notes: List<Note>)

    @Delete
    abstract fun deleteNote(note: Note)

    @Update
    abstract fun updateNote(note: Note)

    @Query("SELECT * FROM notes")
    abstract fun getNotesFlow(): Flow<List<Note>>

}