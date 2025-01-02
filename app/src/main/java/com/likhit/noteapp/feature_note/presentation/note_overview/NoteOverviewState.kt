package com.likhit.noteapp.feature_note.presentation.note_overview

import com.likhit.noteapp.feature_note.domain.Note
import com.likhit.noteapp.feature_note.domain.utils.NoteOrder
import com.likhit.noteapp.feature_note.domain.utils.OrderType

data class NoteOverviewState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
