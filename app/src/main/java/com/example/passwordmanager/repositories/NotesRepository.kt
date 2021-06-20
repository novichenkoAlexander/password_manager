package com.example.passwordmanager.repositories

import com.example.passwordmanager.database.NotesDao
import com.example.passwordmanager.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NotesRepository(private val notesDao: NotesDao) {

    val notesFlow: Flow<List<Note>> = notesDao.getNotesFlow()

    val notDeletedNotesFlow: Flow<List<Note>> = notesDao.getNotDeletedNotesFlow()

    suspend fun saveNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.saveNote(
                Note(
                    login = note.login,
                    password = note.password,
                    siteUrl = note.siteUrl,
                    color = note.color
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

    suspend fun getNoteWithDeletedFlag(): Note {
        return withContext(Dispatchers.IO) {
            notesDao.getNoteWithDeletedFlag()
        }
    }
}
