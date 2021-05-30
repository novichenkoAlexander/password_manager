package com.example.passwordmanager

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PasswordManagerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PasswordManagerApp)
            modules(listOf())
        }
    }

    private val viewModules = module {

    }

    private val databaseModule = module {

    }

    private val repositories = module {

    }
}