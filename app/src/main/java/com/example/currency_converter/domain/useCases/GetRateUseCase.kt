package com.example.currency_converter.domain.useCases

import com.example.currency_converter.Utils.Resource
import com.example.currency_converter.domain.model.CurrencyRate
import com.example.currency_converter.domain.repositoryInt.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetRateUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(base: String): Flow<Resource<CurrencyRate>>{
        return repository.getRates(base)
    }
}