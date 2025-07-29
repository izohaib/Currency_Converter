package com.example.currency_converter.data.repository

import com.example.currency_converter.Utils.Resource
import com.example.currency_converter.data.remote.ApiInterface
import com.example.currency_converter.domain.model.CurrencyRate
import com.example.currency_converter.domain.repositoryInt.AppRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: ApiInterface
): AppRepository {
    override fun getRates(base: String): Flow<Resource<CurrencyRate>> = flow {

        emit(Resource.Loading())

        val gson = Gson()
        try {
            val response =  api.getRates(base.lowercase())

            //only give us specific field and skip other fields from response
            val ratesJson = response.getAsJsonObject(base.lowercase())

            val rates: Map<String, Double> = gson.fromJson(
                ratesJson,
                object : TypeToken<Map<String, Double>>() {}.type
            )
//            val rates: Map<String, Double> = ratesJson.entrySet().associate {
//                it.key to it.value.asDouble
//            }
            val date = response.get("date").asString

            emit(Resource.Success(CurrencyRate(base = base, rates = rates)))

        } catch (e: Exception){
            emit(Resource.Error("Failed to load rates: ${e.localizedMessage}"))
        }
    }
}