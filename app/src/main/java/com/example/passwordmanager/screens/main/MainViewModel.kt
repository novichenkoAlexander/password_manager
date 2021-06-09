package com.example.passwordmanager.screens.main

import androidx.lifecycle.asLiveData
import com.example.passwordmanager.repositories.NotesRepository
import com.example.passwordmanager.repositories.UserRepository
import com.example.passwordmanager.support.CoroutineViewModel

class MainViewModel(private val notesRepository: NotesRepository) : CoroutineViewModel() {

    val notesLiveDate = notesRepository.notesFlow.asLiveData()

}