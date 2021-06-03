package com.example.passwordmanager

import android.app.Application
import com.example.passwordmanager.screens.main.MainViewModel
import com.example.passwordmanager.screens.note.NoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PasswordManagerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PasswordManagerApp)
            modules(listOf(viewModels))
        }
    }

    private val viewModels = module {
        viewModel { MainViewModel() }
        viewModel { NoteViewModel() }
    }

    private val databaseModule = module {

    }

    private val repositories = module {

    }
}