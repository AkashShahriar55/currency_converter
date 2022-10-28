package com.akash.currencyconverter.domain.model

import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class ExchangeRate(){
    @SerializedName("base")
    var base:String = ""
    @SerializedName("date")
    var date:String=""
    @SerializedName("rates")
    var rates:Map<String,Double> = HashMap()

    override fun toString(): String {
        return base + " "  + date + " " + rates.toString()
    }
}
