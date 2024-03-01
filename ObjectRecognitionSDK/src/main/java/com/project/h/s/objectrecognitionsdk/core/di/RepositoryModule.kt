package com.project.h.s.objectrecognitionsdk.core.di

import android.content.Context
import com.project.h.s.objectrecognitionsdk.data.UserSharedPreference
import com.project.h.s.objectrecognitionsdk.data.api.JsonPlaceHolderApiCall
import com.project.h.s.objectrecognitionsdk.data.repository.ApiCallRepositoryImpl
import com.project.h.s.objectrecognitionsdk.data.repository.MobileNetV1ModelImpl
import com.project.h.s.objectrecognitionsdk.domain.repository.ApiCallRepository
import com.project.h.s.objectrecognitionsdk.domain.repository.MobileNetV1Model
import com.project.h.s.objectrecognitionsdk.domain.usecases.LoginUseCase
import com.project.h.s.objectrecognitionsdk.domain.usecases.MobileNetV1UseCase
import com.project.h.s.objectrecognitionsdk.domain.usecases.UserListUseCase
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

    @Provides
    fun provideLoginUseCase(apiCallRepository: ApiCallRepository) = LoginUseCase(apiCallRepository)

    @Provides
    fun provideUserListUseCase(
        apiCallRepository: ApiCallRepository,
        sharedPreference: UserSharedPreference
    ) = UserListUseCase(apiCallRepository, sharedPreference)

    @Provides
    fun provideMobileNetV1UseCase(mobileNetV1Model: MobileNetV1Model) =
        MobileNetV1UseCase(mobileNetV1Model)
}