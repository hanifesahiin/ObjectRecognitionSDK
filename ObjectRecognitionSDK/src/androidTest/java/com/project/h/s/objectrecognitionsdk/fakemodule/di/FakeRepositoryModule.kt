package com.project.h.s.objectrecognitionsdk.fakemodule.di

import com.project.h.s.objectrecognitionsdk.core.di.RepositoryModule
import com.project.h.s.objectrecognitionsdk.domain.repository.ApiCallRepository
import com.project.h.s.objectrecognitionsdk.domain.repository.MobileNetV1Model
import com.project.h.s.objectrecognitionsdk.fakemodule.model.FakeMobileNetV1ModelImpl
import com.project.h.s.objectrecognitionsdk.fakemodule.repository.FakeApiCallRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(replaces = [RepositoryModule::class], components = [SingletonComponent::class])
class FakeRepositoryModule {
    @Provides
    fun provideApiCallRepository(): ApiCallRepository =
        FakeApiCallRepositoryImpl()

    @Provides
    fun provideMobileNetV1Model(): MobileNetV1Model =
        FakeMobileNetV1ModelImpl()
}