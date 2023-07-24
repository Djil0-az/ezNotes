package com.djilocodes.eznotes.note_feature.domain.repository

import com.djilocodes.eznotes.note_feature.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    //fake repository interface good for test case purposes
    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int ):Note?

    suspend fun insertNote(note:Note)

    suspend fun deleteNote(note: Note)
}