package com.djilocodes.eznotes.note_feature.data.repository

import com.djilocodes.eznotes.note_feature.data.data_base.NotesDao
import com.djilocodes.eznotes.note_feature.domain.model.Note
import com.djilocodes.eznotes.note_feature.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImplement(private val dao: NotesDao):NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return  dao.getNoteID(id)
    }

    override suspend fun insertNote(note: Note) {
       return dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
      return dao.deleteNote(note)
    }
}
