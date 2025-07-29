package com.example.currency_converter.domain.model

data class CurrencyRate (
    val base: String,
    val rates: Map<String, Double>
)