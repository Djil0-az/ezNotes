package com.djilocodes.eznotes.note_feature.domain.use_case

import com.djilocodes.eznotes.note_feature.domain.model.InvalidNoteException
import com.djilocodes.eznotes.note_feature.domain.model.Note
import com.djilocodes.eznotes.note_feature.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("Note title cannot be blank!")
        }
        if(note.description.isBlank()){
            throw InvalidNoteException("Note content cannot be empty!")
        }
        repository.insertNote(note)
    }
}