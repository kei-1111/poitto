package com.example.flush.domain.use_case

import com.example.flush.domain.model.User
import com.example.flush.domain.repository.AuthRepository
import com.example.flush.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Flow<User> {
        val uid = authRepository.getCurrentUser()?.uid ?: ""
        return userRepository.getUser(uid)
    }
}
