package com.akash.currencyconverter.architecture.di

import com.akash.currencyconverter.architecture.manager.SessionManager
import com.akash.currencyconverter.data.repository.CommissionRepositoryImplementation
import com.akash.currencyconverter.data.repository.CurrencyExchangeRepositoryImpl
import com.akash.currencyconverter.data.repository.datasource.BalanceLocalDataSource
import com.akash.currencyconverter.data.repository.datasource.CurrencyRemoteDataSource
import com.akash.currencyconverter.domain.repository.CommissionRepository
import com.akash.currencyconverter.domain.repository.CurrencyExchangeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideCurrencyExchangeRepository(
        balanceLocalDataSource: BalanceLocalDataSource,
        currencyRemoteDataSource: CurrencyRemoteDataSource
    ): CurrencyExchangeRepository =
        CurrencyExchangeRepositoryImpl(balanceLocalDataSource, currencyRemoteDataSource)

    @Provides
    fun provideCommissionRepository(
        sessionManager: SessionManager
    ): CommissionRepository =
        CommissionRepositoryImplementation(sessionManager)





}