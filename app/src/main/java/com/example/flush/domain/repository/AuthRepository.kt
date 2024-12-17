package com.example.flush.domain.repository

import android.content.Intent
import androidx.activity.result.IntentSenderRequest
import com.github.michaelbull.result.Result
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?
    suspend fun signUpWithEmail(email: String, password: String): Result<FirebaseUser, String>
    suspend fun signInWithEmail(email: String, password: String): Result<AuthResult, String>
    suspend fun requestGoogleOneTapAuth(): Result<IntentSenderRequest, String>
    suspend fun signInWithGoogle(resultData: Intent): Result<AuthResult, String>
    suspend fun signOut(): Result<Unit, String>
}
