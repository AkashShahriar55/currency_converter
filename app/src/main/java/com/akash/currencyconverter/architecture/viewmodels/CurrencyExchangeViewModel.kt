package com.akash.currencyconverter.architecture.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.currencyconverter.domain.model.Balance
import com.akash.currencyconverter.domain.model.ExchangeRate
import com.akash.currencyconverter.domain.repository.CurrencyExchangeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    private val currencyExchangeRepository: CurrencyExchangeRepository
): ViewModel() {

    val handler = CoroutineExceptionHandler {
            context, exception ->

        println("Caught $exception")
    }

    private var exchangeRate:ExchangeRate? = null


    private var userBalances:LiveData<List<Balance>> = MutableLiveData()
    fun fetchUserBalances(): LiveData<List<Balance>> {
        viewModelScope.launch {
            userBalances = currencyExchangeRepository.fetchUserBalances()
        }
        return userBalances
    }



    private val _availableExchanges = MutableLiveData<List<String>>()
    val availableExchanges:LiveData<List<String>> = _availableExchanges


    fun fetchCurrency(){
        currencyExchangeRepository.fetchCurrencyExchange().onEach {
            Log.d("check_curr", ": " + it)
            exchangeRate = it


            _availableExchanges.value = it.rates.keys.toList()

        }.launchIn(viewModelScope)
    }

    fun validateValue(sellCurrency: String, value: String): String {
        userBalances.value?.let {balances->

            val valueInDouble = value.toDouble()

            for(balance in balances){
                if(sellCurrency == balance.currency && valueInDouble > balance.amount){
                    return balance.amount.toString()
                }
            }
        }

        return value

    }

    fun convertAmount(sellCurrency: String, buyCurrency: String?, actualValue: String): String {
        exchangeRate?.let { exchange->
            buyCurrency?.let { buyCurrency->
                val amountInDouble = actualValue.toDouble()
                if(sellCurrency == exchange.base ){
                    val conversion = exchange.rates[buyCurrency]!! * amountInDouble
                    return String.format("%.2f",conversion)
                }else{
                    val conversion = ( exchange.rates[buyCurrency]!! / exchange.rates[sellCurrency]!!) * amountInDouble
                    return String.format("%.2f",conversion)
                }
            }
        }

        return "0.0"
    }

    init {
       fetchCurrency()
    }



}