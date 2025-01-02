package com.likhit.noteapp.feature_note.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.likhit.noteapp.ui.theme.BabyBlue
import com.likhit.noteapp.ui.theme.LightGreen
import com.likhit.noteapp.ui.theme.RedOrange
import com.likhit.noteapp.ui.theme.RedPink
import com.likhit.noteapp.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
){
    companion object{
        val noteColors = listOf(
            RedOrange,
            LightGreen,
            BabyBlue,
            Violet,
            RedPink
        )
    }
}

class InvalidNoteException(message: String): Exception(message)
