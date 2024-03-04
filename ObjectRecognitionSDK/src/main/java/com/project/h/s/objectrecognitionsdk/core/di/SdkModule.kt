package com.project.h.s.objectrecognitionsdk.core.di

import com.project.h.s.objectrecognitionsdk.ObjectRecognitionSDKInterface
import com.project.h.s.objectrecognitionsdk.ObjectRecognitionSDKInterfaceImpl
import com.project.h.s.objectrecognitionsdk.data.UserSharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SdkModule {
    @Provides
    @Singleton
    fun provideObjectRecognitionSDKInterface(sharedPreference: UserSharedPreference): ObjectRecognitionSDKInterface {
        return ObjectRecognitionSDKInterfaceImpl(sharedPreference)
    }
}