package com.example.passwordmanager.screens.main

import androidx.lifecycle.asLiveData
import com.example.passwordmanager.models.Note
import com.example.passwordmanager.repositories.NotesRepository
import com.example.passwordmanager.support.CoroutineViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel(private val notesRepository: NotesRepository) : CoroutineViewModel() {

    val notesLiveData = notesRepository.notesFlow.asLiveData()

    val undoNotesLiveData = notesRepository.notDeletedNotesFlow.asLiveData()

    fun getNoteWithDeletedFlag(): Note? = runBlocking {
        notesRepository.getNoteWithDeletedFlag()
    }

    fun deleteWithUndo(position: Int, callback: (Note) -> Unit) {
        launch {
            val note = undoNotesLiveData.value?.get(position)
            note?.deleted = true
            note?.let { notesRepository.updateNote(it) }
            note?.let { callback(it) }
        }
    }

    fun updateNote(note: Note) {
        launch {
            notesRepository.updateNote(note)
        }
    }

    fun clearTable() {
        launch {
            notesRepository.clearTableFromGarbage()
        }
    }


}