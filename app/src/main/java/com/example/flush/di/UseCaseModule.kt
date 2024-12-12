package com.example.flush.di

import com.example.flush.domain.repository.AuthRepository
import com.example.flush.domain.repository.UserRepository
import com.example.flush.domain.use_case.SignInWithGoogleUseCase
import com.example.flush.domain.use_case.SignInWithGoogleUseCaseImpl
import com.example.flush.domain.use_case.SignUpWithEmailUseCase
import com.example.flush.domain.use_case.SignUpWithEmailUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSignUpWithEmailUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): SignUpWithEmailUseCase = SignUpWithEmailUseCaseImpl(authRepository, userRepository, ioDispatcher)

    @Provides
    @Singleton
    fun providesSignInWithGoogleUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): SignInWithGoogleUseCase = SignInWithGoogleUseCaseImpl(authRepository, userRepository, ioDispatcher)
}
