package com.djilocodes.eznotes.note_feature.presentation.notes_home_page

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djilocodes.eznotes.note_feature.domain.model.Note
import com.djilocodes.eznotes.note_feature.domain.use_case.NoteUseCase
import com.djilocodes.eznotes.note_feature.domain.util1.NoteOrder
import com.djilocodes.eznotes.note_feature.domain.util1.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
//this class will inherit our use cases and directly interact with UI



@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCase: NoteUseCase
    //notes use cases cases contains all of our use cases such as sorting,deleting and adding
    ):ViewModel(){

    //reference to last deleted note
    private var recentDeletedNote: Note? = null
    //coroutine job to check get note
    private var getNotesJob: Job?= null

    // create state variable to switch states
    //and to remember which state we have
    private val _state= mutableStateOf(NoteState())
    val state: State<NoteState> = _state
    init {
        //default state
        getNote(NoteOrder.Date(OrderType.Descending) )
    }

        //check for events
        fun onEvent(event:NoteEvents){
            when(event){

                is NoteEvents.Order ->{
                    if( // use ::classes because we're not comparing references
                        state.value.noteOrder::class == event.noteOrder::class
                        //asc or dsc order is the same
                        &&  state.value.noteOrder.orderType ==event.noteOrder.orderType )
                        {
                            // if everything is the same then do nothing
                        return
                        }
                    getNote(event.noteOrder)
                }

                is NoteEvents.DeleteNote->{
                    //start viewmodel coroutine
                    viewModelScope.launch {
                        notesUseCase.deleteNoteUseCase(event.note)
                        // create an copy of recently deleted note and store in var
                        recentDeletedNote = event.note
                    }

                }


                is NoteEvents.RestoreNote->{
                    viewModelScope.launch{
                        //check if deleted not is not null after restoring it , set it to null
                        // to prevent duplicate insertion
                        notesUseCase.addNote(recentDeletedNote?: return@launch )
                        recentDeletedNote = null
                    }
                }

                // toggle order menu collapse /expand
                is NoteEvents.ToggleOrderSection->{
                    _state.value = state.value.copy(
                        isOrderSectionVisible = !state.value.isOrderSectionVisible
                    )
                }

            }
        }

    //coroutine flow to  get notes according to the Order
    private fun getNote(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = notesUseCase.getNotesUseCase(noteOrder )
            .onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteOrder = noteOrder)
        }.launchIn(viewModelScope)

    }

}
