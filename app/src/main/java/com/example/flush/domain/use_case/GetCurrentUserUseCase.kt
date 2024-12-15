package com.example.flush.domain.use_case

import com.example.flush.di.IoDispatcher
import com.example.flush.domain.model.User
import com.example.flush.domain.repository.AuthRepository
import com.example.flush.domain.repository.UserRepository
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
