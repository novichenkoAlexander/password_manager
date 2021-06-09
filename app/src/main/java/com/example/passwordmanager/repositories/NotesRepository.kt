package com.example.passwordmanager.repositories

import com.example.passwordmanager.database.NotesDao
import com.example.passwordmanager.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NotesRepository(private val notesDao: NotesDao) {

    suspend fun saveNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.saveNote(note)
        }
    }

    val notesFlow: Flow<List<Note>> = notesDao.getNotesFlow()

}