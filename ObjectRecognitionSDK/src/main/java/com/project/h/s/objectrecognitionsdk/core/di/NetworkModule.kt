package com.project.h.s.objectrecognitionsdk.core.di

import com.google.gson.GsonBuilder
import com.project.h.s.objectrecognitionsdk.data.api.JsonPlaceHolderApiCall
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val _baseUrl = "https://json-placeholder.mock.beeceptor.com"

    @Provides
    fun provideRetrofit(): Retrofit {
        val g = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(_baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(g))
            .build()
    }

    @Provides
    fun provideJsonPlaceHolderApiCall(retrofit: Retrofit): JsonPlaceHolderApiCall {
        return retrofit.create(JsonPlaceHolderApiCall::class.java)
    }
}