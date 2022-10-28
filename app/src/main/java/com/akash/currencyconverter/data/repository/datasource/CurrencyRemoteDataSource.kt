package com.akash.currencyconverter.data.repository.datasource

import com.akash.currencyconverter.domain.model.ExchangeRate
import com.akash.currencyconverter.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface CurrencyRemoteDataSource {

    suspend fun getCurrencyExchangeValue(): Response<ExchangeRate>

}