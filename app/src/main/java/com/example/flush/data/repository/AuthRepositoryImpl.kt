package com.example.flush.data.repository

import com.example.flush.di.IoDispatcher
import com.example.flush.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {
    override suspend fun signUpWithEmail(email: String, password: String): Result<FirebaseUser> =
        withContext(ioDispatcher) {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                Result.success(result.user!!)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override fun getCurrentUser() = auth.currentUser
}
