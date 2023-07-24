package com.djilocodes.eznotes.note_feature.presentation.notes_home_page

import com.djilocodes.eznotes.note_feature.domain.model.Note
import com.djilocodes.eznotes.note_feature.domain.util1.NoteOrder
import com.djilocodes.eznotes.note_feature.domain.util1.OrderType
// this class will show the current UI state of the note screen
// the current radio Button selection
//the current list of our notes
// and see whether these order section are visible or not
data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
