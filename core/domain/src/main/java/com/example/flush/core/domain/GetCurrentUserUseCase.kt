package com.example.flush.core.domain

import com.example.flush.core.di.IoDispatcher
import com.example.flush.core.model.User
import com.example.flush.core.repository.AuthRepository
import com.example.flush.core.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): Flow<User> = withContext(ioDispatcher) {
        val uid = authRepository.getCurrentUser()?.uid ?: ""
        userRepository.getUser(uid)
    }
}
