package com.example.passwordmanager.screens.main

import androidx.lifecycle.asLiveData
import com.example.passwordmanager.models.Note
import com.example.passwordmanager.repositories.NotesRepository
import com.example.passwordmanager.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val notesRepository: NotesRepository) : CoroutineViewModel() {

    val markedAsDeletedNoteLiveData = notesRepository.markedAsDeletedNoteFlow.asLiveData()

    val undoNotesLiveData = notesRepository.notDeletedNotesFlow.asLiveData()


    fun deleteWithUndo(position: Int) {
        launch {
            val note = undoNotesLiveData.value?.get(position)
            note?.deleted = true
            note?.let { notesRepository.updateNote(it) }
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