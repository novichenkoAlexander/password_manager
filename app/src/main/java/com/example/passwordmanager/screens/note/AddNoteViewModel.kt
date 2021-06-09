package com.example.passwordmanager.screens.note

import androidx.lifecycle.MutableLiveData
import com.example.passwordmanager.models.Note
import com.example.passwordmanager.repositories.NotesRepository
import com.example.passwordmanager.support.CoroutineViewModel
import kotlinx.coroutines.launch

class AddNoteViewModel(private val notesRepository: NotesRepository) : CoroutineViewModel() {

    val textEnteredLiveData = MutableLiveData<Boolean>()

    fun checkForEmptyFields(website: String, username: String, password: String) {
        if (website.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
            textEnteredLiveData.postValue(true)
        } else {
            textEnteredLiveData.postValue(false)
        }
    }

    fun saveNote(note: Note) = launch {
        notesRepository.saveNote(note)
    }
}
