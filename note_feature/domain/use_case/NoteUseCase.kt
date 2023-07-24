package com.djilocodes.eznotes.note_feature.domain.use_case
// This class will be injected into view model
// all use cases go here
data class NoteUseCase(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNote: AddNote,
    val getNote: GetNote
)
