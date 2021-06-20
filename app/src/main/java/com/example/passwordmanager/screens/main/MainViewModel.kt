package com.example.passwordmanager.screens.main

import androidx.lifecycle.asLiveData
import com.example.passwordmanager.repositories.NotesRepository
import com.example.passwordmanager.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val notesRepository: NotesRepository) : CoroutineViewModel() {

    val notesLiveDate = notesRepository.notesFlow.asLiveData()

    fun deleteByPos(pos: Int) {
        launch {
            val note = notesLiveDate.value?.get(pos)
            note?.let { notesRepository.deleteNote(it) }
        }
    }

}