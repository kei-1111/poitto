package com.example.flush.domain.use_case

import com.example.flush.di.IoDispatcher
import com.example.flush.domain.model.User
import com.example.flush.domain.repository.AuthRepository
import com.example.flush.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpWithEmailUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : SignUpWithEmailUseCase {
    override suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                val result = authRepository.signUpWithEmail(email, password)
                if (result.isSuccess) {
                    val firebaseUser = result.getOrNull()
                    if (firebaseUser != null) {
                        val user = User(
                            uid = firebaseUser.uid,
                            email = firebaseUser.email ?: "",
                            name = "名無し",
                        )
                        val userResult = userRepository.createUser(user)
                        if (userResult.isSuccess) {
                            Result.success(Unit)
                        } else {
                            Result.failure(userResult.exceptionOrNull() ?: Exception("Register failed"))
                        }
                    } else {
                        Result.failure(Exception("User is null"))
                    }
                } else {
                    Result.failure(result.exceptionOrNull() ?: Exception("Register failed"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
