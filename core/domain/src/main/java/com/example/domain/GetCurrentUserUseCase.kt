package com.example.domain

import com.example.di.IoDispatcher
import com.example.repository.AuthRepository
import com.example.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): Flow<com.example.model.User> = withContext(ioDispatcher) {
        val uid = authRepository.getCurrentUser()?.uid ?: ""
        userRepository.getUser(uid)
    }
}
