package com.djilocodes.eznotes.note_feature.domain.use_case

import com.djilocodes.eznotes.note_feature.domain.model.Note
import com.djilocodes.eznotes.note_feature.domain.repository.NoteRepository
import com.djilocodes.eznotes.note_feature.domain.util1.NoteOrder
import com.djilocodes.eznotes.note_feature.domain.util1.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//get Notes use case with the ability to sort notes according to date,name,color in ASC or DSC order

class GetNotesUseCase(private val repository: NoteRepository) {
    //pass args to invoke so that we can know how to sort notes when we get them
    // make 2 utility classes and pass object as arg

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>>{
        //return a flow type of notes from our repository and map to the list
    return repository.getNotes().map { notes->
            when(noteOrder.orderType){
                //order type according to asc order for color/date/title
                is OrderType.Ascending->{
                    when(noteOrder){
                        is NoteOrder.Title ->notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date ->notes.sortedBy { it.timestamp }
                        is NoteOrder.Color ->notes.sortedBy { it.color}
                    }
                }



                is OrderType.Descending->{
                    when(noteOrder){
                        is NoteOrder.Title ->notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date ->notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Color ->notes.sortedByDescending { it.color}
                    }
                }
            }
    }
    }
}