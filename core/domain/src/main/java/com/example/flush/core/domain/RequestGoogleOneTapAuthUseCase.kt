package com.example.flush.core.domain

import com.example.flush.core.di.IoDispatcher
import com.example.flush.core.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestGoogleOneTapAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke() = withContext(ioDispatcher) {
        authRepository.requestGoogleOneTapAuth()
    }
}
