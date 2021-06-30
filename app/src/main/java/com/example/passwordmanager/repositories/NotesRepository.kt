package com.example.passwordmanager.repositories

import com.example.passwordmanager.database.NotesDao
import com.example.passwordmanager.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NotesRepository(private val notesDao: NotesDao) {


    val notDeletedNotesFlow: Flow<List<Note>> = notesDao.getNotDeletedNotesFlow()

    val markedAsDeletedNoteFlow: Flow<Note?> = notesDao.getNoteWithDeletedFlag()

    suspend fun saveNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.saveNote(
                Note(
                    login = note.login,
                    password = note.password,
                    siteUrl = note.siteUrl,
                    color = note.color,
                    deleted = note.deleted
                )
            )
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.updateNote(note)
        }
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.deleteNote(note)
        }
    }

    suspend fun clearTableFromGarbage() {
        withContext(Dispatchers.IO) {
            notesDao.clearTableFromNotUndoNotes()
        }
    }
}

