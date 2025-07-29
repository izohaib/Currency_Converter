package com.example.currency_converter.presentation

import com.example.currency_converter.domain.model.CurrencyRate

data class ApiState (
    val loading: Boolean = false,
    val data: CurrencyRate? = null,
    val error: String = ""
)