package com.likhit.noteapp.feature_note.domain

import com.likhit.noteapp.feature_note.domain.utils.NoteOrder
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(noteOrder: NoteOrder): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun upsertNote(note: Note)

    suspend fun deleteNote(note: Note)
}