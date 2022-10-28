package com.akash.currencyconverter.data.repository.datasourceimpl

import android.util.Log
import com.akash.currencyconverter.data.dao.BalanceDao
import com.akash.currencyconverter.data.repository.datasource.BalanceLocalDataSource
import com.akash.currencyconverter.domain.model.Balance
import com.akash.currencyconverter.domain.model.Response
import com.akash.currencyconverter.domain.model.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class BalanceLocalDataSourceImpl(val balanceDao: BalanceDao) :BalanceLocalDataSource {
    override suspend fun updateOrInsertBalances(balances: List<Balance>): Flow<Response<Int>> {
        val result = balanceDao.upsert(balances)
        return flow{
            if(result > 0)
                emit(Response<Int>(Status.Successful,"Successfully updated balance",null,result))
            else
                emit(Response<Int>(Status.Failed,"no balance was updated",null,result))
        }
    }

    override suspend fun getAllBalances(): Flow<Response<List<Balance>>> {
        return balanceDao.getAllBalance().map { balances ->
            Log.d("check", "getAllBalances: "+balances)
            if(balances.isNotEmpty())
                Response<List<Balance>>(Status.Successful,"Successfully updated balance",null,balances)
            else
                Response<List<Balance>>(Status.Failed,"no balance was found",null, arrayListOf())
        }
    }
}