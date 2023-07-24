package com.djilocodes.eznotes.note_feature.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djilocodes.eznotes.note_feature.domain.model.InvalidNoteException
import com.djilocodes.eznotes.note_feature.domain.model.Note
import com.djilocodes.eznotes.note_feature.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AddEditNoteVM @Inject constructor(
    private val noteUseCases : NoteUseCase,
    // savedState bundle contains Navigation arguments
    // hilt automatically injects and takes care of the saved state
    savedStateHandle: SavedStateHandle
): ViewModel()
{
    // 3 main states(which are persistent)  for color , title and content
    //one state for the Hint that goes in noteTitle
    // event flow for saving the note
    // use a navigation argument to get current note Id when clicked

    private val _noteTitle = mutableStateOf(NoteTextField(hint = "Insert Title..."))

    val noteTitle: State<NoteTextField> = _noteTitle

    // note Content
    private val _noteDescription = mutableStateOf(NoteTextField(hint = "Insert Description..."))

    val noteDescription: State<NoteTextField> = _noteDescription
    //note color set a random color as our default

    private val _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())

    val noteColor: State<Int> = _noteColor
    // we make a flow for the ui event to show the snack bar


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Id of the current Note
    private var currentNoteId: Int?= null
    //
    init {
        //gets note ID as int and if its not null we can do something with the id
        savedStateHandle.get<Int>("noteId")?.let{ noteId ->
            // -1 is the ID of the note that is going to be added when we click the + FAB
            if(noteId!= -1){
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note->
                        // note goes here
                        currentNoteId = note.id
                        // update value of our title , we take title from Db and put it in textfield
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )

                        // update value of our Content , we take it from Db and put it in textfield
                        _noteDescription.value = noteDescription.value.copy(
                            text = note.description,
                            isHintVisible = false
                        )

                        // update value of our color , we take it from Db and put it in textfield
                        _noteColor.value = note.color

                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent){
        when(event){
            //set title
            is AddEditEvent.EnterTitle->{
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value)
            }
            // focus on title/ change title
            is AddEditEvent.noHintTitle->{
                _noteTitle.value = noteTitle.value.copy(
                // hide the hint when text is blank
            isHintVisible = !event.focusState.isFocused &&
                    noteTitle.value.text.isBlank()
            )}

            //set content
            is AddEditEvent.EnterDescription->{_noteDescription.value =
                noteDescription.value.copy(text = event.value) }
            // focus on content
            is AddEditEvent.noHintDescription->{_noteDescription.value =
                noteDescription.value.copy(
                    // hide the hint when text is blank
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )}

            // color circles click
            is AddEditEvent.changeColor ->{_noteColor.value = event.color}
            //save note
            is AddEditEvent.SaveNote ->{
                viewModelScope.launch {
                    try{ noteUseCases.addNote(Note(
                        title = noteTitle.value.text,
                        description = noteDescription.value.text,
                        timestamp = System.currentTimeMillis(),
                        color = noteColor.value,
                        id = currentNoteId
                    ))
                    _eventFlow.emit(UiEvent.SaveNote)
                    }
                    // if we get an error on saving note

                    catch (e: InvalidNoteException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = e.message?:"Couldn't Save Note!"))
                    }
                }
            }
        }
    }
    // create a class for Ui events
    sealed class UiEvent{
        // show snackbar, save note, navigate back , flip screen

        data class ShowSnackBar(val message: String):UiEvent()
        object SaveNote: UiEvent()
    }

    }