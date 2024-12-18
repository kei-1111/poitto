package com.example.flush.data.repository

import android.net.Uri
import android.util.Log
import com.example.flush.domain.model.User
import com.example.flush.domain.repository.UserRepository
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : UserRepository {
    private val userCollection = firestore.collection("users")

    override suspend fun createUser(user: User): Result<Unit, String> =
        try {
            userCollection.document(user.uid).set(user).await()
            Ok(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error creating user", e)
            val errorMessage = getErrorMessage(e)
            Err(errorMessage)
        }

    override suspend fun getUser(uid: String): Flow<User> = callbackFlow {
        val docRef = firestore.collection("users").document(uid)
        val listener = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val user = snapshot.toObject(User::class.java)
                if (user != null) {
                    trySend(user).isSuccess
                }
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun saveUser(user: User) =
        try {
            val currentUser = userCollection.document(user.uid).get().await()
                .toObject(User::class.java)

            val uploadUserIconResult = handleUploadUserIcon(currentUser, user)

            val updateData = mutableMapOf<String, Any>(
                "name" to user.name,
            )

            if (uploadUserIconResult.isOk) {
                updateData["iconUrl"] = uploadUserIconResult.value
            }

            userCollection.document(user.uid).update(updateData).await()

            Ok(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "saveUser: Error updating user data", e)
            val errorMessage = getErrorMessage(e)
            Err(errorMessage)
        }

    private suspend fun handleUploadUserIcon(
        currentUser: User?,
        user: User,
    ): Result<String, String> {
        return if (currentUser?.iconUrl != user.iconUrl) {
            user.iconUrl?.let { uploadUserIcon(user.uid, Uri.parse(it)) }
                ?: Err("ユーザアイコンのアップロードに失敗しました")
        } else {
            user.iconUrl?.let { Ok(it) }
                ?: Err("ユーザアイコンのアップロードに失敗しました")
        }
    }

    private suspend fun uploadUserIcon(uid: String, imageUri: Uri): Result<String, String> =
        try {
            val userIconRef = storage.reference.child(("user_icons/$uid/icon.jpg"))
            userIconRef.putFile(imageUri).await()
            val result = userIconRef.downloadUrl.await().toString()
            Ok(result)
        } catch (e: Exception) {
            Log.e(TAG, "updateUserIcon: ", e)
            val errorMessage = getErrorMessage(e)
            Err(errorMessage)
        }

    companion object {
        private const val TAG = "UserRepositoryImpl"

        private fun getErrorMessage(e: Exception): String {
            return when (e) {
                is FirebaseFirestoreException -> "データベースエラーが発生しました"
                else -> "予期せぬエラーが発生しました"
            }
        }
    }
}
