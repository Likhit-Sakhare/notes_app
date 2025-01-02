package com.likhit.noteapp.feature_note.presentation.note_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likhit.noteapp.feature_note.domain.Note
import com.likhit.noteapp.feature_note.domain.NoteRepository
import com.likhit.noteapp.feature_note.domain.utils.NoteOrder
import com.likhit.noteapp.feature_note.domain.utils.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteOverviewViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {

    var state by mutableStateOf(NoteOverviewState())
        private set

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onAction(action: NoteOverviewAction){
        when(action){
            is NoteOverviewAction.OnOrderClick -> {
                if(state.noteOrder::class == action.noteOrder::class &&
                    state.noteOrder.orderType == action.noteOrder.orderType){
                    return
                }
                getNotes(action.noteOrder)
            }
            is NoteOverviewAction.OnDeleteClick -> {
                viewModelScope.launch {
                    noteRepository.deleteNote(action.note)
                    recentlyDeletedNote = action.note
                }
            }
            NoteOverviewAction.OnRestoreClick -> {
                viewModelScope.launch {
                    noteRepository.upsertNote(recentlyDeletedNote?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            NoteOverviewAction.OnToggleClick -> {
                state = state.copy(
                    isOrderSectionVisible = !state.isOrderSectionVisible
                )
            }
            else -> Unit
        }
    }

    private fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()
        getNotesJob = noteRepository.getNotes(noteOrder)
            .onEach { notes: List<Note> ->
                state = state.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }
}