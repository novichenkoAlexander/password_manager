package com.example.passwordmanager.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.passwordmanager.repositories.UserRepository
import com.example.passwordmanager.support.CoroutineViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class CreatePasswordViewModel(private val userRepository: UserRepository) : CoroutineViewModel() {

    val statusLiveData = MutableLiveData<String>()

    val passwordCreated : LiveData<Boolean> = userRepository.checkPasswordCreated().asLiveData()

    fun createPassword(password: Long) = launch {
        try {
            when {
                password.toString().isNotEmpty() ->
                    if (userRepository.createPassword(password)) {
                        statusLiveData.postValue("Password Created")
                    } else {
                        statusLiveData.postValue("Error")
                    }
            }
        } catch (e: Exception) {
            statusLiveData.postValue(e.message)
        }
    }

}