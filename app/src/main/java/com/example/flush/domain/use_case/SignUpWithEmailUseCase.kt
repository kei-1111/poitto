package com.example.flush.domain.use_case

interface SignUpWithEmailUseCase {
    suspend operator fun invoke(email: String, password: String): Result<Unit>
}
