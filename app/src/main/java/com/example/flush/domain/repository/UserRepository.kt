package com.example.flush.domain.repository

import com.example.flush.domain.model.User
import com.google.android.play.integrity.internal.u
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User): Result<Unit>
    suspend fun getUser(uid: String): Flow<User>
    suspend fun saveUser(user: User): Result<Unit>
}
