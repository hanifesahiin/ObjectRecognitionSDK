package com.project.h.s.objectrecognitionsdk.core.di

import com.project.h.s.objectrecognitionsdk.data.api.JsonPlaceHolderApiCall
import com.project.h.s.objectrecognitionsdk.data.repository.ApiCallRepositoryImpl
import com.project.h.s.objectrecognitionsdk.domain.repository.ApiCallRepository
import com.project.h.s.objectrecognitionsdk.domain.usecases.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideApiCallRepository(jsonPlaceHolderApiCall: JsonPlaceHolderApiCall): ApiCallRepository =
        ApiCallRepositoryImpl(jsonPlaceHolderApiCall)

    @Provides
    fun provideLoginUseCase(apiCallRepository: ApiCallRepository) = LoginUseCase(apiCallRepository)
}