package com.likhit.noteapp.feature_note.presentation.add_note

import androidx.compose.ui.graphics.toArgb
import com.likhit.noteapp.feature_note.domain.Note

data class AddNoteState(
    val titleText: String = "",
    val contentText: String = "",
    val titleHint: String = "",
    val contentHint: String = "",
    val isTitleHintVisible: Boolean = false,
    val isContentHintVisible: Boolean = false,
    val noteColor: Int = Note.noteColors.random().toArgb()
)
