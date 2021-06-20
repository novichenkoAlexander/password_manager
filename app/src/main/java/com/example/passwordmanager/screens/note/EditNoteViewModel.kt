package com.example.passwordmanager.screens.note

import com.example.passwordmanager.models.Note
import com.example.passwordmanager.repositories.NotesRepository
import com.example.passwordmanager.support.CoroutineViewModel
import kotlinx.coroutines.launch

class EditNoteViewModel(private val notesRepository: NotesRepository) : CoroutineViewModel() {


    fun deleteNote(note: Note) = launch {
        notesRepository.deleteNote(note)
    }

    fun updateNote(note: Note) = launch {
        notesRepository.updateNote(note)
    }

}