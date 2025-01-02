package com.likhit.noteapp.feature_note.presentation.note_overview

import com.likhit.noteapp.feature_note.domain.Note
import com.likhit.noteapp.feature_note.domain.utils.NoteOrder

sealed class NoteOverviewAction {
    data class OnOrderClick(val noteOrder: NoteOrder): NoteOverviewAction()
    data class OnDeleteClick(val note: Note): NoteOverviewAction()
    data object OnRestoreClick: NoteOverviewAction()
    data object OnToggleClick: NoteOverviewAction()
    data object OnAddClick: NoteOverviewAction()
}