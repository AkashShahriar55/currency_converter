package com.akash.currencyconverter.data.converters

import com.akash.currencyconverter.domain.model.ExchangeRate
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MyDeserialization : JsonDeserializer<ExchangeRate?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ExchangeRate {
        val data = ExchangeRate()
        val base = json.getAsJsonObject()["base"].asString
        val date = json.getAsJsonObject()["date"].asString
        val `object`: JsonObject = json.getAsJsonObject().getAsJsonObject(rates)
        val retMap: Map<String, Double> =
            Gson().fromJson(`object`, object : TypeToken<HashMap<String?, Double?>?>() {}.type)

        data.base = base
        data.date = date
        data.rates = retMap
        return data
    }

    companion object {
        private const val rates = "rates"
    }
}