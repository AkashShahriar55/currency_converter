package com.akash.currencyconverter.architecture.di

import com.akash.currencyconverter.data.api.CurrencyApi
import com.akash.currencyconverter.data.repository.datasource.CurrencyRemoteDataSource
import com.akash.currencyconverter.data.repository.datasourceimpl.CurrencyRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    @Singleton
    fun provideCurrencyRemoteDataSource(currencyApi: CurrencyApi): CurrencyRemoteDataSource {
        return CurrencyRemoteDataSourceImpl(currencyApi)
    }
}