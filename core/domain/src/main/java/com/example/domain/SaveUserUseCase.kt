package com.example.domain

import com.example.di.IoDispatcher
import com.example.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(user: com.example.model.User) = withContext(ioDispatcher) {
        userRepository.saveUser(user)
    }
}
