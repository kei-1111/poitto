package com.example.di

import com.example.data.api.EmotionAnalysisApi
import com.example.repository.AuthRepositoryImpl
import com.example.repository.EmotionAnalysisRepositoryImpl
import com.example.repository.ThrowingItemRepositoryImpl
import com.example.repository.UserRepositoryImpl
import com.example.repository.AuthRepository
import com.example.repository.EmotionAnalysisRepository
import com.example.repository.ThrowingItemRepository
import com.example.repository.UserRepository
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        signInClient: SignInClient,
    ): AuthRepository = AuthRepositoryImpl(auth, signInClient)

    @Provides
    @Singleton
    fun provideUserRepository(
        firestore: FirebaseFirestore,
        storage: FirebaseStorage,
    ): UserRepository = UserRepositoryImpl(firestore, storage)

    @Provides
    @Singleton
    fun provideEmotionAnalysisRepository(
        emotionAnalysisApi: EmotionAnalysisApi,
    ): EmotionAnalysisRepository = EmotionAnalysisRepositoryImpl(emotionAnalysisApi)

    @Provides
    @Singleton
    fun provideThrowingItemRepository(
        firestore: FirebaseFirestore,
        storage: FirebaseStorage,
    ): ThrowingItemRepository = ThrowingItemRepositoryImpl(firestore, storage)
}
