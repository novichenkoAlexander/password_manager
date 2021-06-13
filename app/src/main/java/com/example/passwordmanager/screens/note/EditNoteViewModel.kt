package com.example.passwordmanager.screens.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.passwordmanager.models.Note
import com.example.passwordmanager.repositories.NotesRepository
import com.example.passwordmanager.support.CoroutineViewModel
import kotlinx.coroutines.launch

class EditNoteViewModel(private val notesRepository: NotesRepository) : CoroutineViewModel() {

    var noteDeletedLiveData = MutableLiveData<Boolean>()

    fun deleteNote(note: Note) = launch {
        notesRepository.deleteNote(note)
    }

    fun checkIfNoteDeleted(){

    }

    fun updateNote(note: Note) = launch {
        notesRepository.updateNote(note)
    }

    companion object{
        const val DELETED = "DELETED"
    }
}