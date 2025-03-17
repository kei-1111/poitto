package com.example.flush.core.domain

import android.util.Log
import com.example.flush.core.di.IoDispatcher
import com.example.flush.core.model.User
import com.example.flush.core.repository.AuthRepository
import com.example.flush.core.repository.UserRepository
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit, String> {
        return withContext(ioDispatcher) {
            try {
                authRepository.signUpWithEmail(email, password)
                    .andThen { firebaseUser ->
                        val user = User(
                            uid = firebaseUser.uid,
                            email = firebaseUser.email ?: "",
                            name = "名無し",
                            iconUrl = "https://firebasestorage.googleapis.com/v0/b/flush-de17e.firebasestorage.app/o/" +
                                "default_icon.png?alt=media&token=c77ba166-ca8a-4504-b70e-c4749c8f9cfc",
                        )
                        userRepository.createUser(user)
                    }
                    .map { Unit }
            } catch (e: Exception) {
                Log.e(TAG, "Sign up failed", e)
                Err(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        private const val TAG = "SignUpWithEmailUseCase"
    }
}
