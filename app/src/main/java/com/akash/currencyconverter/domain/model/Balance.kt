package com.akash.currencyconverter.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "balance_table"
)
data class Balance(
    var currency:String = "",
    var amount:Double = 0.0
){
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
