package com.example.passwordmanager.repositories

import com.example.passwordmanager.database.UserDao
import com.example.passwordmanager.datastore.AppSettings
import com.example.passwordmanager.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao, private val appSettings: AppSettings) {

    suspend fun login(password: Long): Boolean {
        return withContext(Dispatchers.IO) {
            checkPassword(password)
        }
    }

    suspend fun createPassword(password: Long): Boolean {
        var passwordCreated: Boolean
        withContext(Dispatchers.IO) {
            passwordCreated = if (checkForUserExists().not() && password.toString().length >= 4) {
                userDao.addNewUser(User(password = password))
                appSettings.setPassword(password)
                true
            } else {
                false
            }
        }
        return passwordCreated
    }

    fun checkPasswordCreated(): Flow<Boolean> =
        appSettings.passwordFlow().map { it != 0L }.flowOn(Dispatchers.IO)


    private suspend fun checkPassword(password: Long): Boolean {
        return withContext(Dispatchers.IO) {
            appSettings.getPassword() == password
        }
    }

    private suspend fun checkForUserExists(): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.getUsersCount() > 0
        }
    }
}