package com.example.flush.di

import android.util.Log
import com.example.flush.data.api.EmotionAnalysisApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Dns
import okhttp3.EventListener
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://p-text-generate.onrender.com/"

    private const val TimeOutSeconds = 90L
    private const val MaxRequests = 64
    private const val MaxRequestsPerHost = 5

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TimeOutSeconds, TimeUnit.SECONDS)
            .readTimeout(TimeOutSeconds, TimeUnit.SECONDS)
            .writeTimeout(TimeOutSeconds, TimeUnit.SECONDS)
            .eventListener(object : EventListener() {
                override fun callStart(call: Call) {
                    super.callStart(call)
                }

                override fun callEnd(call: Call) {
                    super.callEnd(call)
                }
            })
            .dispatcher(
                okhttp3.Dispatcher().apply {
                    maxRequests = MaxRequests
                    maxRequestsPerHost = MaxRequestsPerHost
                },
            )
            .dns(Dns.SYSTEM)
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(json: Json, client: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideEmotionAnalysisApi(retrofit: Retrofit): EmotionAnalysisApi {
        return retrofit.create(EmotionAnalysisApi::class.java)
    }
}
