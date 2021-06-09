package com.example.passwordmanager

import android.app.Application
import com.example.passwordmanager.database.DataBaseConstructor
import com.example.passwordmanager.database.PasswordAppDatabase
import com.example.passwordmanager.datastore.AppSettings
import com.example.passwordmanager.repositories.UserRepository
import com.example.passwordmanager.screens.login.CreatePasswordViewModel
import com.example.passwordmanager.screens.login.LoginViewModel
import com.example.passwordmanager.screens.main.MainViewModel
import com.example.passwordmanager.screens.note.AddNoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PasswordManagerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PasswordManagerApp)
            modules(listOf(viewModels, databaseModule, repositories))
        }
    }

    private val viewModels = module {
        viewModel { MainViewModel() }
        viewModel { AddNoteViewModel() }
        viewModel { CreatePasswordViewModel(get()) }
        viewModel { LoginViewModel(get()) }
    }

    private val databaseModule = module {
        single { DataBaseConstructor.create(get()) }
        factory { get<PasswordAppDatabase>().notesDao() }
        factory { get<PasswordAppDatabase>().userDao() }
        single { AppSettings(get()) }
    }

    private val repositories = module {
        factory { UserRepository(get(), get()) }

    }
}