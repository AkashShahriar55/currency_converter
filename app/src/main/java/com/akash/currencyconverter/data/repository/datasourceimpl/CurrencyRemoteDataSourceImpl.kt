package com.akash.currencyconverter.data.repository.datasourceimpl

import android.util.Log
import com.akash.currencyconverter.data.api.CurrencyApi
import com.akash.currencyconverter.data.converters.MyDeserialization
import com.akash.currencyconverter.data.repository.datasource.CurrencyRemoteDataSource
import com.akash.currencyconverter.domain.model.ExchangeRate
import com.akash.currencyconverter.domain.model.Response
import com.akash.currencyconverter.domain.model.Status
import com.google.gson.GsonBuilder


class CurrencyRemoteDataSourceImpl(private val currencyApi: CurrencyApi) :CurrencyRemoteDataSource {
    override suspend fun getCurrencyExchangeValue():Response<ExchangeRate> {
        val response = currencyApi.getLatestExchangeRate()
        val gson = GsonBuilder()
            .registerTypeAdapter(ExchangeRate::class.java, MyDeserialization()).create()

        if(response.isSuccessful && response.body() != null){
            val data = gson.fromJson(response.body()!!.string(),ExchangeRate::class.java)
            return Response(Status.Successful,"Successfully updated currency exchange",null,data)
        }else{
            return Response(Status.Failed,"failed to updated currency exchange",null,null)
        }
    }
}