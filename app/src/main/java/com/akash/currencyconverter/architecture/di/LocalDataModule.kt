package com.akash.currencyconverter.architecture.di

import com.akash.currencyconverter.data.dao.BalanceDao
import com.akash.currencyconverter.data.repository.datasource.BalanceLocalDataSource
import com.akash.currencyconverter.data.repository.datasourceimpl.BalanceLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    fun provideCurrencyLocalDataSource(balanceDao: BalanceDao): BalanceLocalDataSource =
        BalanceLocalDataSourceImpl(balanceDao)
}