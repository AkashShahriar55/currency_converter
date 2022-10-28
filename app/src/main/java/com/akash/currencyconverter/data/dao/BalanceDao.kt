package com.akash.currencyconverter.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.akash.currencyconverter.data.db.BalanceDb
import com.akash.currencyconverter.domain.model.Balance
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BalanceDao : BaseDao<Balance>() {

    @Query("select * from balance_table")
    abstract fun getAllBalance(): Flow<List<Balance>>

    @Query("select * from balance_table where id == :id")
    abstract fun getBalanceById(id:Int):Flow<Balance>
}