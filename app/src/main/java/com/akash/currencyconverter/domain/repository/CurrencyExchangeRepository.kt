package com.akash.currencyconverter.domain.repository

import androidx.lifecycle.LiveData
import com.akash.currencyconverter.domain.model.Balance
import com.akash.currencyconverter.domain.model.ExchangeRate
import com.akash.currencyconverter.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface CurrencyExchangeRepository {

    suspend fun getAvailableConvertCurrency():LiveData<List<String>>


    fun convertBalance(from:String,to:String,amount:Double):Double

    fun validateAmount(currency:String,amount: Double):Double

    suspend fun submitConvert(from: String,to: String,amount: Double):LiveData<Response<Double>>


    suspend fun fetchUserBalances(): LiveData<List<Balance>>
    fun fetchCurrencyExchange(): Flow<ExchangeRate>
}