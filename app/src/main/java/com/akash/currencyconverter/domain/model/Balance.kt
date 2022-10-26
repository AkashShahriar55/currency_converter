package com.akash.currencyconverter.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Balance(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var currency:String = "",
    var amount:Double = 0.0
)
