package com.example.passwordmanager.screens.login

import androidx.lifecycle.MutableLiveData
import com.example.passwordmanager.repositories.UserRepository
import com.example.passwordmanager.support.CoroutineViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(private val userRepository: UserRepository) : CoroutineViewModel() {

    val statusLivaData = MutableLiveData<String>()

    fun login(password: String) = launch {
        try {
            val loggedIn = userRepository.login(password.toLong())
            if (!loggedIn) {
                statusLivaData.postValue(WRONG_PASSWORD)
            } else {
                statusLivaData.postValue(SUCCESS)
            }
        } catch (e: Exception) {
            statusLivaData.postValue(e.message)
        }

    }

    companion object{
        const val SUCCESS = "SUCCESS"
        const val WRONG_PASSWORD = "WRONG PASSWORD"
    }

}