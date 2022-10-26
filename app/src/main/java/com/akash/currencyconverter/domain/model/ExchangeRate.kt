package com.akash.currencyconverter.domain.model

data class ExchangeRate(
    var base:String,
    var date:String,
    var rates:Map<String,Double>
)
