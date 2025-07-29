package com.example.currency_converter.Utils

sealed class Resource<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    //success holds the CurrencyRate
    class Success<T>(private val success: T?) : Resource<T>(data = success)
    class Error<T>(private val error: String) : Resource<T>(errorMessage = error)
    class Loading<T> : Resource<T>()
}