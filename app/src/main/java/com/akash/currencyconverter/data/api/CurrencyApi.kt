package com.akash.currencyconverter.data.api

import com.akash.currencyconverter.domain.model.ExchangeRate
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyApi {
    @GET("latest")
    suspend fun getLatestExchangeRate(): Response<ResponseBody>
}