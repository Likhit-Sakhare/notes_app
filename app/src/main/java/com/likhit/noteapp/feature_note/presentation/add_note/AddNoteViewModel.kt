package com.likhit.noteapp.feature_note.presentation.add_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.likhit.noteapp.feature_note.domain.InvalidNoteException
import com.likhit.noteapp.feature_note.domain.Note
import com.likhit.noteapp.feature_note.domain.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(AddNoteState(
        titleHint = "Enter title...",
        contentHint = "Enter some content..."
    ))
        private set

    private val _eventChannel = Channel<AddNoteEvent>()
    val events = _eventChannel.receiveAsFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { notedId ->
            if(notedId != -1){
                viewModelScope.launch {
                    noteRepository.getNoteById(notedId)?.also { note ->
                        currentNoteId = note.id
                        state = state.copy(
                            titleText = note.title,
                            contentText = note.content,
                            isTitleHintVisible = false,
                            isContentHintVisible = false,
                            noteColor = note.color
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: AddNoteAction){
        when(action){
            is AddNoteAction.EnteredTitle -> {
                state = state.copy(
                    titleText = action.titleValue
                )
            }
            is AddNoteAction.ChangeTitleFocus -> {
                state = state.copy(
                    isTitleHintVisible = !action.titleFocusState.isFocused &&
                        state.titleText.isBlank()
                )
            }
            is AddNoteAction.EnteredContent -> {
                state = state.copy(
                    contentText = action.contentValue
                )
            }
            is AddNoteAction.ChangeContentFocus -> {
                state = state.copy(
                    isContentHintVisible = !action.contentFocusState.isFocused &&
                        state.contentText.isBlank()
                )
            }
            is AddNoteAction.ChangeColor -> {
                state = state.copy(
                    noteColor = action.color
                )
            }
            AddNoteAction.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteRepository.upsertNote(
                            Note(
                                title = state.titleText,
                                content = state.contentText,
                                timestamp = System.currentTimeMillis(),
                                color = state.noteColor,
                                id = currentNoteId
                            )
                        )
                        _eventChannel.send(AddNoteEvent.SaveNote)
                    }catch (e: InvalidNoteException){
                        _eventChannel.send(
                            AddNoteEvent.ShowToast(
                                message = e.message?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }
}