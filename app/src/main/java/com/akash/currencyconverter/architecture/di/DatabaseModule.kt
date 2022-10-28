package com.akash.currencyconverter.architecture.di

import android.app.Application
import com.akash.currencyconverter.data.dao.BalanceDao
import com.akash.currencyconverter.data.db.BalanceDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(app: Application): BalanceDb =
        BalanceDb.getInstance(app)

    @Provides
    fun provideBalanceDao(balanceDb: BalanceDb) : BalanceDao= balanceDb.balanceDao

}