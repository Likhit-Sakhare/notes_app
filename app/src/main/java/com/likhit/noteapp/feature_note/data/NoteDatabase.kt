package com.likhit.noteapp.feature_note.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.likhit.noteapp.feature_note.domain.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}