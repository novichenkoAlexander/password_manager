package com.example.passwordmanager.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Note(
    val id: Long = 0L,
    val login: String = "",
    val password: String = "",
    val siteUrl: String = ""
) : Parcelable