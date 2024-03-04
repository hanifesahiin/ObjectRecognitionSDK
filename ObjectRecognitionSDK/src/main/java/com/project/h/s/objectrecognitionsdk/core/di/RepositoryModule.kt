package com.project.h.s.objectrecognitionsdk.core.di

import android.content.Context
import com.project.h.s.objectrecognitionsdk.data.api.JsonPlaceHolderApiCall
import com.project.h.s.objectrecognitionsdk.data.repository.ApiCallRepositoryImpl
import com.project.h.s.objectrecognitionsdk.data.repository.MobileNetV1ModelImpl
import com.project.h.s.objectrecognitionsdk.domain.repository.ApiCallRepository
import com.project.h.s.objectrecognitionsdk.domain.repository.MobileNetV1Model
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideApiCallRepository(jsonPlaceHolderApiCall: JsonPlaceHolderApiCall): ApiCallRepository =
        ApiCallRepositoryImpl(jsonPlaceHolderApiCall)

    @Provides
    fun provideMobileNetV1Model(@ApplicationContext context: Context): MobileNetV1Model =
        MobileNetV1ModelImpl(context)
}