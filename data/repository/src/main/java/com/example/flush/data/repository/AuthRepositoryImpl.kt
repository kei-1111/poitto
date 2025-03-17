package com.example.flush.data.repository

import android.content.Intent
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import com.example.flush.core.repository.AuthRepository
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val signInClient: SignInClient,
) : AuthRepository {

    private val signInRequest: BeginSignInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build(),
        )
        .setAutoSelectEnabled(true)
        .build()

    override fun getCurrentUser() = auth.currentUser

    override suspend fun signUpWithEmail(email: String, password: String): Result<FirebaseUser, String> =
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            if (firebaseUser == null) {
                Err("ユーザーが作成されませんでした")
            } else {
                Ok(firebaseUser)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Sign up failed", e)
            val errorMessage = getErrorMessage(e)
            Err(errorMessage)
        }

    override suspend fun signInWithEmail(email: String, password: String): Result<AuthResult, String> =
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Ok(result)
        } catch (e: Exception) {
            Log.e(TAG, "Sign in failed", e)
            val errorMessage = getErrorMessage(e)
            Err(errorMessage)
        }

    override suspend fun requestGoogleOneTapAuth(): Result<IntentSenderRequest, String> =
        try {
            val result = signInClient.beginSignIn(signInRequest).await()
            val pendingIntent = result.pendingIntent
            val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent).build()
            Ok(intentSenderRequest)
        } catch (e: Exception) {
            Log.e(TAG, "Error requesting Google One Tap", e)
            val errorMessage = getErrorMessage(e)
            Err(errorMessage)
        }

    override suspend fun signInWithGoogle(resultData: Intent): Result<AuthResult, String> =
        try {
            val credential = signInClient.getSignInCredentialFromIntent(resultData)
            val idToken = credential.googleIdToken
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(firebaseCredential).await()
            Ok(result)
        } catch (e: Exception) {
            Log.e(TAG, "Error signing in with Google One Tap", e)
            val errorMessage = getErrorMessage(e)
            Err(errorMessage)
        }

    override suspend fun signOut(): Result<Unit, String> =
        try {
            val result = auth.signOut()
            Ok(result)
        } catch (e: Exception) {
            Log.e(TAG, "Sign out failed", e)
            val errorMessage = getErrorMessage(e)
            Err(errorMessage)
        }

    companion object {
        private const val TAG = "AuthRepositoryImpl"

        private fun getErrorMessage(e: Exception): String {
            return when (e) {
                is FirebaseAuthInvalidUserException -> "ユーザーが存在しません"
                is FirebaseAuthUserCollisionException -> "このメールアドレスは既に使用されています"

                is FirebaseAuthInvalidCredentialsException -> "メールアドレスまたはパスワードが無効です"

                is FirebaseAuthException -> "認証エラーが発生しました"

                else -> "予期せぬエラーが発生しました"
            }
        }
    }
}
