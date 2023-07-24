package com.djilocodes.eznotes.note_feature.presentation.notes_home_page

import com.djilocodes.eznotes.note_feature.domain.model.Note
import com.djilocodes.eznotes.note_feature.domain.util1.NoteOrder
// all events go here such as edit,delete,add,order
sealed class NoteEvents{
    data class Order(val noteOrder: NoteOrder):NoteEvents() // will order items when clicked on
    data class DeleteNote(val note: Note): NoteEvents()// delete note
    object RestoreNote: NoteEvents()// undo delete
    object ToggleOrderSection: NoteEvents()// order menu
}
