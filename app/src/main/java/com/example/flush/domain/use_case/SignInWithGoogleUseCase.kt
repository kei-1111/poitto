package com.example.flush.domain.use_case

import android.content.Intent

interface SignInWithGoogleUseCase {
    suspend operator fun invoke(resultData: Intent): Result<Unit>
}