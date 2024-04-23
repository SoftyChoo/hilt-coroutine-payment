package com.example.note_mvvm_compose.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


}

/**
 *  // 어플리케이션 전체에서 사용할 NoteDB의 인스턴스 제공
 *     @Provides
 *     @Singleton
 *     fun provideNoteDatabase(app: Application): NoteDataBase {
 *         // 제공된 Application 컨텍스트와 NoteDB 클래스를 사용해 RoomDB 빌드
 *         return Room.databaseBuilder(
 *             app,
 *             NoteDataBase::class.java,
 *             NoteDataBase.DATABASE_NAME
 *         ).build()
 *     }
 *
 *     @Provides
 *     @Singleton
 *     fun provideNoteRepository(db: NoteDataBase): NoteRepository {
 *         // NoteDB 로부터 얻은 NoteDao를 사용해 NoteRepositoryImpl 인스턴스화
 *         return NoteRepositoryImpl(db.noteDao)
 *     }
 *
 *     @Provides
 *     @Singleton
 *     fun provideNoteUseCases(repository: NoteRepository) : NoteUseCases{
 *         return NoteUseCases(
 *             getNotesUseCase = GetNotesUseCase(repository),
 *             deleteNoteUseCase = DeleteNoteUseCase(repository),
 *             addNoteUseCase = AddNoteUseCase(repository),
 *             getNoteUseCase = GetNoteUseCase(repository)
 *         )
 *     }
 */