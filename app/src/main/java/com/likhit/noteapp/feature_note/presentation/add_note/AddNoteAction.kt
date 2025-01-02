package com.likhit.noteapp.feature_note.presentation.add_note

import androidx.compose.ui.focus.FocusState

sealed class AddNoteAction {
    data class EnteredTitle(val titleValue: String): AddNoteAction()
    data class ChangeTitleFocus(val titleFocusState: FocusState): AddNoteAction()
    data class EnteredContent(val contentValue: String): AddNoteAction()
    data class ChangeContentFocus(val contentFocusState: FocusState): AddNoteAction()
    data class ChangeColor(val color: Int): AddNoteAction()
    data object SaveNote: AddNoteAction()
}