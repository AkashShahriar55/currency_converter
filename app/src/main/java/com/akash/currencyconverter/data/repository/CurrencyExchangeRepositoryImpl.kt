package com.akash.currencyconverter.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.akash.currencyconverter.data.repository.datasource.BalanceLocalDataSource
import com.akash.currencyconverter.data.repository.datasource.CurrencyRemoteDataSource
import com.akash.currencyconverter.domain.model.Balance
import com.akash.currencyconverter.domain.model.ExchangeRate
import com.akash.currencyconverter.domain.model.Response
import com.akash.currencyconverter.domain.model.Status
import com.akash.currencyconverter.domain.repository.CurrencyExchangeRepository
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive

class CurrencyExchangeRepositoryImpl(
    val balanceLocalDataSource: BalanceLocalDataSource,
    val currencyRemoteDataSource: CurrencyRemoteDataSource
)
    : CurrencyExchangeRepository {


    private var userBalances:List<Balance> = arrayListOf()
    private val exchangeRate:ExchangeRate? = null





    override suspend fun fetchUserBalances(): LiveData<List<Balance>> {
        return balanceLocalDataSource.getAllBalances().map {
            if(it.status == Status.Successful && it.data != null)
                it.data
            else
                arrayListOf()
        }.asLiveData()
    }


    override fun fetchCurrencyExchange(): Flow<ExchangeRate> {
        return flow {
            while (currentCoroutineContext().isActive){
                val response = currencyRemoteDataSource.getCurrencyExchangeValue()
                if(response.status == Status.Successful && response.data != null){
                    emit(response.data)
                }

                delay(1000000000)
            }
        }

    }



    val globalResponse:MutableLiveData<Response<Any>> = MutableLiveData<Response<Any>>()


    override suspend fun getAvailableConvertCurrency(): LiveData<List<String>> {
        TODO("Not yet implemented")
    }


    override fun convertBalance(from: String, to: String, amount: Double): Double {
        TODO("Not yet implemented")
    }

    override fun validateAmount(currency: String, amount: Double): Double {
        TODO("Not yet implemented")
    }

    override suspend fun submitConvert(
        from: String,
        to: String,
        amount: Double
    ): LiveData<Response<Double>> {
        TODO("Not yet implemented")
    }
}