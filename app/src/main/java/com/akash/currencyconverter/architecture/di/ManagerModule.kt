package com.akash.currencyconverter.architecture.di

import android.content.Context
import com.akash.currencyconverter.architecture.manager.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context) : SessionManager{
        return SessionManager(context)
    }
}