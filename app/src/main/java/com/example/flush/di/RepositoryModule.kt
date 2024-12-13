package com.example.flush.di

import com.example.flush.data.api.EmotionAnalysisApi
import com.example.flush.data.repository.AuthRepositoryImpl
import com.example.flush.data.repository.EmotionAnalysisRepositoryImpl
import com.example.flush.data.repository.UserRepositoryImpl
import com.example.flush.domain.repository.AuthRepository
import com.example.flush.domain.repository.EmotionAnalysisRepository
import com.example.flush.domain.repository.UserRepository
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        signInClient: SignInClient,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): AuthRepository = AuthRepositoryImpl(auth, signInClient, ioDispatcher)

    @Provides
    @Singleton
    fun provideUserRepository(
        firestore: FirebaseFirestore,
        storage: FirebaseStorage,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): UserRepository = UserRepositoryImpl(firestore, storage, ioDispatcher)

    @Provides
    @Singleton
    fun provideEmotionAnalysisRepository(
        emotionAnalysisApi: EmotionAnalysisApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): EmotionAnalysisRepository = EmotionAnalysisRepositoryImpl(emotionAnalysisApi, ioDispatcher)
}
