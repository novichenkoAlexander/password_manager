package com.example.passwordmanager.screens.note

import androidx.lifecycle.MutableLiveData
import com.example.passwordmanager.support.CoroutineViewModel

class AddNoteViewModel() : CoroutineViewModel() {

    val textEnteredLiveData = MutableLiveData<Boolean>()

    fun checkForEmptyFields(website: String, username: String, password: String) {
        if (website.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
            textEnteredLiveData.postValue(true)
        } else {
            textEnteredLiveData.postValue(false)
        }
    }
}
