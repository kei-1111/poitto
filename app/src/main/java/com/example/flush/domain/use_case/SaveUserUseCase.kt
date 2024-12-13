package com.example.flush.domain.use_case

import com.example.flush.domain.model.User
import com.example.flush.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(user: User) = userRepository.saveUser(user)
}
