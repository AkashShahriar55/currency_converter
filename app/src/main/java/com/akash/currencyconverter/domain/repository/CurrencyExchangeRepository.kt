package com.akash.currencyconverter.domain.repository

import androidx.lifecycle.LiveData
import com.akash.currencyconverter.domain.model.Balance
import com.akash.currencyconverter.domain.model.ExchangeRate
import com.akash.currencyconverter.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface CurrencyExchangeRepository {

    suspend fun submitConvert(from: Balance,to: Balance):LiveData<Response<Int>>


    suspend fun fetchUserBalances(): LiveData<List<Balance>>
    fun fetchCurrencyExchange(): Flow<ExchangeRate>
}