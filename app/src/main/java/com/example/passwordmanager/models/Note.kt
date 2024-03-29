package com.example.passwordmanager.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")

data class Note(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val login: String = "",
    val password: String = "",
    val siteUrl: String = "",
    val color: Int = 0,
    var deleted: Boolean = false,
) : Parcelable