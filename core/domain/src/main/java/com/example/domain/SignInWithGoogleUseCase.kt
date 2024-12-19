package com.example.domain

import android.content.Intent
import android.util.Log
import com.example.di.IoDispatcher
import com.example.model.User
import com.example.repository.AuthRepository
import com.example.repository.UserRepository
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.github.michaelbull.result.Result

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(resultData: Intent): Result<Unit, String> =
        withContext(ioDispatcher) {
            try {
                authRepository.signInWithGoogle(resultData)
                    .andThen { authResult ->
                        val user = User(
                            uid = authResult.user?.uid ?: "",
                            email = authResult.user?.email ?: "",
                            name = "名無し",
                            iconUrl = authResult.user?.photoUrl.toString(),
                        )
                        userRepository.createUser(user)
                    }
                    .map { Unit }
            } catch (e: Exception) {
                Log.e(TAG, "Error signing in with Google", e)
                Err(e.message ?: "Unknown error")
            }
        }

    companion object {
        private const val TAG = "SignInWithGoogleUseCase"
    }
}
