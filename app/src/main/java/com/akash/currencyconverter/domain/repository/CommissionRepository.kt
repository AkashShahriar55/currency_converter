package com.akash.currencyconverter.domain.repository

interface CommissionRepository {

    fun calculateCommission(currency:String,amount:Double):Double

    fun deductCommission()

}