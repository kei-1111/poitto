package com.example.flush.data.repository

import android.net.Uri
import android.util.Log
import com.example.flush.di.IoDispatcher
import com.example.flush.domain.model.User
import com.example.flush.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : UserRepository {
    private val userCollection = firestore.collection("users")

    override suspend fun createUser(user: User): Result<Unit> =
        try {
            userCollection.document(user.uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
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

            if (uploadUserIconResult.isSuccess) {
                uploadUserIconResult.getOrNull()?.let {
                    updateData["iconUrl"] = it
                }
            }

            userCollection.document(user.uid).update(updateData).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "saveUser: Error updating user data", e)
            Result.failure(e)
        }

    private suspend fun handleUploadUserIcon(
        currentUser: User?,
        user: User,
    ): Result<String> {
        return if (currentUser?.iconUrl != user.iconUrl) {
            user.iconUrl?.let { uploadUserIcon(user.uid, Uri.parse(it)) }
                ?: Result.failure(IllegalArgumentException("User iconUrl is null"))
        } else {
            user.iconUrl?.let { Result.success(it) }
                ?: Result.failure(IllegalArgumentException("User iconUrl is null"))
        }
    }

    private suspend fun uploadUserIcon(uid: String, imageUri: Uri): Result<String> =
        try {
            val userIconRef = storage.reference.child(("user_icons/$uid/icon.jpg"))
            userIconRef.putFile(imageUri).await()
            val result = userIconRef.downloadUrl.await().toString()
            Result.success(result)
        } catch (e: Exception) {
            Log.e(TAG, "updateUserIcon: ", e)
            Result.failure(e)
        }

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }
}
