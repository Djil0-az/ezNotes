package com.djilocodes.eznotes.note_feature.data.data_base

import androidx.room.*
import com.djilocodes.eznotes.note_feature.domain.model.Note
import kotlinx.coroutines.flow.Flow

// this interface will have the functions that will deal with database access
//Insert,Delete,Get single note, get all notes
@Dao
interface NotesDao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>> // read more on flows

    //this note will load a specific Note so that we can edit it
    @Query("SELECT * FROM note WHERE id= :id")
    suspend fun getNoteID(id: Int):Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Note)

    @Delete
    suspend fun deleteNote(note:Note)

}