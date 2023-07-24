package com.djilocodes.eznotes.note_feature.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditEvent {
    data class EnterTitle(val value: String) : AddEditEvent()
    data class EnterDescription(val value:String): AddEditEvent()
    //to hide the hint
    data class noHintTitle(val focusState: FocusState): AddEditEvent()
    data class noHintDescription(val focusState: FocusState): AddEditEvent()
    // color change
    data class changeColor(val color: Int): AddEditEvent()
    //savebtn
    object SaveNote: AddEditEvent()


}