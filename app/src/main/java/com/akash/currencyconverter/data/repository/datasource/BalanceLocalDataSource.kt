package com.akash.currencyconverter.data.repository.datasource

import com.akash.currencyconverter.data.dao.BalanceDao
import com.akash.currencyconverter.domain.model.Balance
import com.akash.currencyconverter.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface BalanceLocalDataSource {
    suspend fun updateOrInsertBalances(balances: List<Balance>):Flow<Response<Int>>
    suspend fun getAllBalances():Flow<Response<List<Balance>>>
}