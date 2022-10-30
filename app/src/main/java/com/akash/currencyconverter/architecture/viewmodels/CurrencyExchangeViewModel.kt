package com.akash.currencyconverter.architecture.viewmodels

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.currencyconverter.domain.model.Balance
import com.akash.currencyconverter.domain.model.ExchangeRate
import com.akash.currencyconverter.domain.repository.CommissionRepository
import com.akash.currencyconverter.domain.repository.CurrencyExchangeRepository
import com.akash.currencyconverter.round
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    private val currencyExchangeRepository: CurrencyExchangeRepository,
    private val commissionRepository: CommissionRepository
): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        // 1. Trigger event to prompt error dialog
        // 2. Log to tracking system for observability
    }
    private val job = SupervisorJob()
    private val context = Dispatchers.Main + job + exceptionHandler

    protected val coroutineScope = CoroutineScope(context)

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

        }.launchIn(coroutineScope)
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

    fun convertAmount(sellCurrency: String, buyCurrency: String?, actualValue: String): Double {
        exchangeRate?.let { exchange->
            buyCurrency?.let { buyCurrency->
                val amountInDouble = actualValue.toDouble()
                if(sellCurrency == exchange.base ){
                    val conversion = exchange.rates[buyCurrency]!! * amountInDouble
                    return conversion.round(2)
                }else{
                    val conversion = ( exchange.rates[buyCurrency]!! / exchange.rates[sellCurrency]!!) * amountInDouble
                    return conversion.round(2)
                }
            }
        }

        return 0.0
    }

    fun validateAndConvert(sellCurrency: String?, buyCurrency: String?, text: Editable?) {
        sellCurrency?.let { sell->
            buyCurrency?.let { buy->
                text?.let { amountString->
                    if(sell != buy && amountString.isNotEmpty()){

                        val amount = amountString.toString().toDouble()
                        val commission = commissionRepository.calculateCommission(sellCurrency,amount)

                        val totalAmount = (amount + commission).round(2)

                        val convertedAmount = convertAmount(sellCurrency,buyCurrency,amountString.toString())


                        if(convertedAmount == 0.0)
                            return


                        userBalances.value?.let {balances->
                            val sellBalance = Balance(sellCurrency,0.0)
                            val buyBalance = Balance(buyCurrency,convertedAmount)

                            for(balance in balances){
                                if(sellCurrency == balance.currency && totalAmount > balance.amount){
                                    return
                                }else if(sellCurrency == balance.currency && totalAmount <= balance.amount){
                                    Log.d("check", "validateAndConvert: " + balance.id + " " + balance.currency)
                                    sellBalance.id = balance.id
                                    sellBalance.amount = (balance.amount - totalAmount).round(2)
                                }

                                if(buyCurrency == balance.currency){
                                    buyBalance.id = balance.id
                                    buyBalance.amount = (balance.amount + convertedAmount).round(2)
                                }
                            }


                            viewModelScope.launch(Dispatchers.Default) {
                                currencyExchangeRepository.submitConvert(sellBalance,buyBalance)
                                commissionRepository.deductCommission()
                            }

                            return "You have converted $amount EUR to 110.00 USD. Commission Fee - 0.70 EUR."




                        }


                    }
                }
            }
        }
    }

    init {
       fetchCurrency()



    }



}