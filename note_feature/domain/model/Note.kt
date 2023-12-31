package com.djilocodes.eznotes.note_feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.djilocodes.eznotes.ui.theme.*
import java.lang.Exception

@Entity // here is our notes table
data class Note (
    val title: String,
    val description: String,
    val timestamp : Long,
    val color: Int,
    @PrimaryKey val id:Int? = null
        ){
    companion object {
        val noteColors = listOf(RedOrange, Green, Blue, Violet,Yellow,Pink)
    }

}
// class to handle various exceptions
class InvalidNoteException(message: String): Exception(message)