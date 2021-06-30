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

    val passwordCreated: LiveData<Boolean> = userRepository.checkPasswordCreated().asLiveData()

    fun createPassword(password: String) = launch {
        try {
            if (password.isNotEmpty() && userRepository.createPassword(password.toLong())) {
                statusLiveData.postValue(PASSWORD_CREATED_SUCCESSFUL)
            } else {
                statusLiveData.postValue(ERROR_CREATING_PASSWORD)
            }

        } catch (e: Exception) {
            statusLiveData.postValue(e.message)
        }
    }

    companion object {
        const val PASSWORD_CREATED_SUCCESSFUL = "Password created successful"
        const val ERROR_CREATING_PASSWORD = "Password must be at least 4 characters long "
    }

}