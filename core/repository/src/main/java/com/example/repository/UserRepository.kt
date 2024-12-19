package com.example.repository


import com.example.model.User
import kotlinx.coroutines.flow.Flow
import com.github.michaelbull.result.Result

interface UserRepository {
    suspend fun createUser(user: User): Result<Unit, String>
    suspend fun getUser(uid: String): Flow<User>
    suspend fun saveUser(user: User): Result<Unit, String>
}
