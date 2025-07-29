package com.example.currency_converter.domain.repositoryInt

import com.example.currency_converter.Utils.Resource
import com.example.currency_converter.domain.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getRates(base: String) : Flow<Resource<CurrencyRate>>
}