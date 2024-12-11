package com.example.flush.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signUpWithEmail(email: String, password: String): Result<FirebaseUser>
    fun getCurrentUser(): FirebaseUser?
}
