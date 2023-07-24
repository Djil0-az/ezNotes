package com.djilocodes.eznotes.note_feature.domain.use_case

import com.djilocodes.eznotes.note_feature.domain.model.Note
import com.djilocodes.eznotes.note_feature.domain.repository.NoteRepository

// this class will get a single note by its ID
class GetNote (
    private val repository: NoteRepository
){
    suspend operator fun invoke(id:Int): Note?{
        return repository.getNoteById(id)
    }
}