package com.example.flush.core.domain

import com.example.flush.core.di.IoDispatcher
import com.example.flush.core.model.User
import com.example.flush.core.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(user: User) = withContext(ioDispatcher) {
        userRepository.saveUser(user)
    }
}
