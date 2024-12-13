package com.example.flush.data.repository

import android.util.Log
import com.example.flush.di.IoDispatcher
import com.example.flush.domain.model.User
import com.example.flush.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
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
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : UserRepository {
    private val userCollection = firestore.collection("users")

    override suspend fun createUser(user: User): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                userCollection.document(user.uid).set(user).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
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
    }.flowOn(ioDispatcher)

    override suspend fun saveUser(user: User) =
        withContext(ioDispatcher) {
            try {
                userCollection.document(user.uid).update(
                    mapOf(
                        "name" to user.name,
                    ),
                ).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "updateUser: ", e)
                Result.failure(e)
            }
        }

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }
}
