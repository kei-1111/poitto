package com.example.flush.core.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SignInClientModule {

    @Provides
    @Singleton
    fun provideSignInClient(
        @ApplicationContext context: Context,
    ): SignInClient = Identity.getSignInClient(context)
}
