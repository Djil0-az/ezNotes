package com.djilocodes.eznotes.dependency_injection

import android.app.Application
import androidx.room.Room
import com.djilocodes.eznotes.note_feature.data.data_base.NoteDataBase
import com.djilocodes.eznotes.note_feature.data.repository.NoteRepositoryImplement
import com.djilocodes.eznotes.note_feature.domain.repository.NoteRepository
import com.djilocodes.eznotes.note_feature.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// module for all dependencies with given life time most will be Singletons
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    // requires application context
    fun provideNoteDatabase(app:Application):NoteDataBase{
        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            NoteDataBase.DATABASE_NAME
        ).build()
    }

    // function that requires repository
    @Provides
    @Singleton
    // provide database instance instead of dao to retrive corresponding dao
    fun provideNoteRepository(db:NoteDataBase): NoteRepository{
        return NoteRepositoryImplement(db.notesDao)
    }
    /* test Case using fake repository
      @Provides
    @Singleton

    fun provideNoteRepositoryTest(db:NoteDataBase): NoteRepository{
        return NoteRepository(db.notesDao)
    }
    * */
    @Provides
    @Singleton

    fun provideNoteUseCases(repository: NoteRepository): NoteUseCase{
        return NoteUseCase(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )

    }
}