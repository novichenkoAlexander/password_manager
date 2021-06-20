package com.example.passwordmanager.database

import androidx.room.*
import com.example.passwordmanager.models.Note
import kotlinx.coroutines.flow.Flow


@Dao
abstract class NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveNote(note: Note): Long

    @Delete
    abstract fun deleteNote(note: Note)

    @Update
    abstract fun updateNote(note: Note)

    @Query("SELECT * FROM notes")
    abstract fun getNotesFlow(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE deleted = 0")
    abstract fun getNotDeletedNotesFlow(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id==:noteId")
    abstract fun getNoteById(noteId: Long): Note

    @Query("DELETE FROM notes WHERE deleted = 1")
    abstract fun clearTableFromNotUndoNotes()

    @Query("SELECT * FROM notes WHERE deleted = 1")
    abstract fun getNoteWithDeletedFlag(): Note
}