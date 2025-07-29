package com.example.currency_converter.presentation.HomeScreen


data class HomeUiStates (
    val fromCurrencyCode: String = "usd",
    val fromCountryCode: String = "us",
    val toCurrencyCode: String = "pkr",
    val toCountryCode: String = "pk",
    val currentAmount: String = "",
    val convertedAmount: Double = 0.0,
    val searchQuery: String = ""
)