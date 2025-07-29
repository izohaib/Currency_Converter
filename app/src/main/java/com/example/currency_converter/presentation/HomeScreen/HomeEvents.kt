package com.example.currency_converter.presentation.HomeScreen

sealed class HomeEvents {
    data class FromCurrencySelected(val currencyCode: String, val countryCode: String) : HomeEvents()
    data class ToCurrencyChangeSelected(val currencyCode: String, val countryCode: String) : HomeEvents()
    data class SearchQueryChange(val query: String) : HomeEvents()
    //result of currency amount here
    data class CurrencyAmount(val amount: String) : HomeEvents()
    object SwapCurrency : HomeEvents()
}