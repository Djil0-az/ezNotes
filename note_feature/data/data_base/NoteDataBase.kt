package com.djilocodes.eznotes.note_feature.data.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.djilocodes.eznotes.note_feature.domain.model.Note

@Database(entities = [Note::class], version = 1)

abstract class NoteDataBase: RoomDatabase() {

    abstract  val notesDao:NotesDao

    companion object{
        const val  DATABASE_NAME = "notes_db"
    }
}