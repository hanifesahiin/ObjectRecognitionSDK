package com.project.h.s.objectrecognitionsdk.core.di

import com.project.h.s.objectrecognitionsdk.data.UserSharedPreference
import com.project.h.s.objectrecognitionsdk.domain.repository.ApiCallRepository
import com.project.h.s.objectrecognitionsdk.domain.repository.MobileNetV1Model
import com.project.h.s.objectrecognitionsdk.domain.usecases.LoginUseCase
import com.project.h.s.objectrecognitionsdk.domain.usecases.MobileNetV1UseCase
import com.project.h.s.objectrecognitionsdk.domain.usecases.UserListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
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