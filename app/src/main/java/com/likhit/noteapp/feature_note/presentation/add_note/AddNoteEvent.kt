package com.likhit.noteapp.feature_note.presentation.add_note

sealed class AddNoteEvent {
    data class ShowToast(val message: String): AddNoteEvent()
    data object SaveNote: AddNoteEvent()
}