package com.example.currency_converter.data.remote

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("currencies/{base}.json")
    suspend fun getRates(@Path("base") base: String): JsonObject
}