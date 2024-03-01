package com.project.h.s.objectrecognitionsdk.core.di

import android.content.Context
import com.project.h.s.objectrecognitionsdk.data.UserSharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    fun provideUserSharedPreference(@ApplicationContext context: Context) = UserSharedPreference(context)
}