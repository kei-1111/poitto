package com.example.flush.domain.use_case

import com.example.flush.di.IoDispatcher
import com.example.flush.domain.model.User
import com.example.flush.domain.repository.UserRepository
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
