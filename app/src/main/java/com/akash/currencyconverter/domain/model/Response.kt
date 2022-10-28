package com.akash.currencyconverter.domain.model

import java.lang.Exception

enum class Status{
    Successful,
    Error,
    Failed
}
data class Response<out T>(
    val status: Status,
    val message:String,
    val exception: Exception?,
    val data:T?
)
